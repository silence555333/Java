package kw.kd.dss.util

import java.io._
import java.net.URI
import java.util._


import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._

import org.apache.zookeeper.common.IOUtils

/**
  * <Description> 通过scala操作HDFS<br>
  *
  * @author yangfei<br>
  * @taskId: <br>
  * @version 1.0<br>
  * @createDate 2020/03/18 9:29 <br>
  */
object HDFSUtil {
  val activenode=ZookeeperClient.getActiveNode()
//  val hdfsUrl = "hdfs://"+activenode+":8020"
val hdfsUrl = "hdfs://m5.server:8020"
  var realUrl = ""

  /**
    * make a new dir in the hdfs
    *
    * @param dir the dir may like '/tmp/testdir'
    * @return boolean true-success, false-failed
    */
  def mkdir(dir : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config,"hdfs")
      if (!fs.exists(new Path(realUrl))) {
        fs.mkdirs(new Path(realUrl))
      }
      fs.close()
      result = true
    }
    result
  }

  /**
    * delete a dir in the hdfs.
    * if dir not exists, it will throw FileNotFoundException
    *
    * @param dir the dir may like '/tmp/testdir'
    * @return boolean true-success, false-failed
    *
    */
  def deleteDir(dir : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config)
      fs.delete(new Path(realUrl), true)
      fs.close()
      result = true
    }
    result
  }

  /**
    * list files/directories/links names under a directory, not include embed
    * objects
    *
    * @param dir a folder path may like '/tmp/testdir'
    * @return List<String> list of file names
    */
  def listAll(dir : String) : List[String] = {
    val names : List[String] = new ArrayList[String]()
    if (StringUtils.isNoneBlank(dir)) {
      realUrl = hdfsUrl + dir
      val config = new Configuration()
      val fs = FileSystem.get(URI.create(realUrl), config)
      val stats = fs.listStatus(new Path(realUrl))
      for (i <- 0 to stats.length - 1) {
        if (stats(i).isFile) {
          names.add(stats(i).getPath.toString)
        } else if (stats(i).isDirectory) {
          names.add(stats(i).getPath.toString)
        } else if (stats(i).isSymlink) {
          names.add(stats(i).getPath.toString)
        }
      }
    }
    names
  }

  /**
    * upload the local file to the hds,
    * notice that the path is full like /tmp/test.txt
    * if local file not exists, it will throw a FileNotFoundException
    *
    * @param localFile local file path, may like F:/test.txt or /usr/local/test.txt
    *
    * @param hdfsFile hdfs file path, may like /tmp/dir
    * @return boolean true-success, false-failed
    *
    **/
  def uploadLocalFile2HDFS(localFile : String, hdfsFile : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(localFile) && StringUtils.isNoneBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(hdfsUrl), config)
      val src = new Path(localFile)
      val dst = new Path(realUrl)
      hdfs.copyFromLocalFile(src, dst)
      hdfs.close()
      result = true
    }
    result
  }

  def downloadHDFS2Localfile(hdfsFile : String,localFile : String): Boolean ={
    var result = false
    if (StringUtils.isNoneBlank(localFile) && StringUtils.isNoneBlank(hdfsFile)) {
//      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(hdfsUrl), config)
      val src = new Path(hdfsFile)
      val dst = new Path(localFile)

      hdfs.copyToLocalFile(false,src,dst,true)
      hdfs.close()
      result = true
    }
    result
  }

  /**
    * create a new file in the hdfs. notice that the toCreateFilePath is the full path
    *  and write the content to the hdfs file.

    * create a new file in the hdfs.
    * if dir not exists, it will create one
    *
    * @param newFile new file path, a full path name, may like '/tmp/test.txt'
    * @param content file content
    * @return boolean true-success, false-failed
    **/
  def createNewHDFSFile(newFile : String, content : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(newFile) && null != content) {
      val urlread = hdfsUrl + newFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(urlread), config)
      val os = hdfs.create(new Path(urlread))
      os.write(content.getBytes("UTF-8"))
      os.close()
      hdfs.close()
      result = true
    }
    result
  }

  /**
    * delete the hdfs file
    *
    * @param hdfsFile a full path name, may like '/tmp/test.txt'
    * @return boolean true-success, false-failed
    */
  def deleteHDFSFile(hdfsFile : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val path = new Path(realUrl)
      val isDeleted = hdfs.delete(path, true)
      hdfs.close()
      result = isDeleted
    }
    result
  }

  /**
    * read the hdfs file content
    *
    * @param hdfsFile a full path name, may like '/tmp/test.txt'
    * @return byte[] file content
    */
  def readHDFSFile(hdfsFile : String) : Array[Byte] = {
    var result =  new Array[Byte](0)
    if (StringUtils.isNoneBlank(hdfsFile)) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      val hdfs = FileSystem.get(URI.create(realUrl), config)
      val path = new Path(realUrl)
      if (hdfs.exists(path)) {
        val inputStream = hdfs.open(path)
        val stat = hdfs.getFileStatus(path)
        val length = stat.getLen.toInt
        val buffer = new Array[Byte](length)
        inputStream.readFully(buffer)
        inputStream.close()
        hdfs.close()
        result = buffer
      }
    }
    result
  }

  /**
    * append something to file dst
    *
    * @param hdfsFile a full path name, may like '/tmp/test.txt'
    * @param content string
    * @return boolean true-success, false-failed
    */
  def append(hdfsFile : String, content : String) : Boolean = {
    var result = false
    if (StringUtils.isNoneBlank(hdfsFile) && null != content) {
      val urlread = hdfsUrl + hdfsFile
      val config = new Configuration()
      config.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER")
      config.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true")
      val hdfs = FileSystem.get(URI.create(urlread), config)
      val path = new Path(urlread)
      if (hdfs.exists(path)) {
        val inputStream = new ByteArrayInputStream(content.getBytes())
        val outputStream = hdfs.append(path)
        IOUtils.copyBytes(inputStream, outputStream, 4096, true)
        outputStream.close()
        inputStream.close()
        hdfs.close()
        result = true
      }
    }
    else {
      HDFSUtil.createNewHDFSFile(hdfsFile, content);
      result = true
    }
    result
  }

  /**
    * append something to file dst
    * need hdfs file is exists
    * @param hdfsFile
    * @param content
    * @return
    */
  def appendContent(hdfsFile:String,content:String):Boolean={
    var result = false
    if (StringUtils.isNoneBlank(hdfsFile) && null != content) {
      realUrl = hdfsUrl + hdfsFile
      val config = new Configuration()
      config.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER")
      config.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true")
      config.setBoolean("dfs.support.append", true)
      val outputStream=getOutputStream(realUrl,config)
      val inputStream=new ByteArrayInputStream(content.getBytes())
      IOUtils.copyBytes(inputStream, outputStream, 4096, true)
      outputStream.close()
      inputStream.close()

    }
    result
  }

  def getOutputStream(path: String, conf: Configuration): FSDataOutputStream = {
    val dfsPath = new Path(path)
    val dfs = getFileSystemForPath(dfsPath, conf)
    // If the file exists and we have append support, append instead of creating a new file
    val stream: FSDataOutputStream = {
      if (dfs.isFile(dfsPath)) {
        if (conf.getBoolean("dfs.support.append", true) ||
          conf.getBoolean("hdfs.append.support", false) ||
          dfs.isInstanceOf[RawLocalFileSystem]) {
          dfs.append(dfsPath)
        } else {
          throw new IllegalStateException("File exists and there is no append support!")
        }
      } else {
        // we dont' want to use hdfs erasure coding, as that lacks support for append and hflush
        SparkHadoopUtil.createNonECFile(dfs, dfsPath)
      }
    }
    stream
  }
  def getFileSystemForPath(path: Path, conf: Configuration): FileSystem = {
    // For local file systems, return the raw local file system, such calls to flush()
    // actually flushes the stream.
    val fs = path.getFileSystem(conf)
    fs match {
      case localFs: LocalFileSystem => localFs.getRawFileSystem
      case _ => fs
    }
  }
}
