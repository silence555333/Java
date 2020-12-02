package kw.kd.dss.util

import java.io.IOException

import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}

object SparkHadoopUtil {
  // scalastyle:off line.size.limit
  /**
    * Create a path that uses replication instead of erasure coding (ec), regardless of the default
    * configuration in hdfs for the given path.  This can be helpful as hdfs ec doesn't support
    * hflush(), hsync(), or append()
    * https://hadoop.apache.org/docs/r3.0.0/hadoop-project-dist/hadoop-hdfs/HDFSErasureCoding.html#Limitations
    */
  // scalastyle:on line.size.limit
  def createNonECFile(fs: FileSystem, path: Path): FSDataOutputStream = {
    try {
      // Use reflection as this uses APIs only available in Hadoop 3
      val builderMethod = fs.getClass().getMethod("createFile", classOf[Path])
      // the builder api does not resolve relative paths, nor does it create parent dirs, while
      // the old api does.
      if (!fs.mkdirs(path.getParent())) {
        throw new IOException(s"Failed to create parents of $path")
      }
      val qualifiedPath = fs.makeQualified(path)
      val builder = builderMethod.invoke(fs, qualifiedPath)
      val builderCls = builder.getClass()
      // this may throw a NoSuchMethodException if the path is not on hdfs
      val replicateMethod = builderCls.getMethod("replicate")
      val buildMethod = builderCls.getMethod("build")
      val b2 = replicateMethod.invoke(builder)
      buildMethod.invoke(b2).asInstanceOf[FSDataOutputStream]
    } catch {
      case  _: NoSuchMethodException =>
        // No createFile() method, we're using an older hdfs client, which doesn't give us control
        // over EC vs. replication.  Older hdfs doesn't have EC anyway, so just create a file with
        // old apis.
        fs.create(path)
    }
  }
}
