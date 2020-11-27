package kw.kd.dss.service


import org.springframework.stereotype.Component

/**
  * @Description TODO
  * @author yangfei
  * @create 2020-11-25 15:49
  * @version 1.0 
  */
@Component
class BMLService {
//
//  def download(userName: String, resourceId: String, version: String): util.Map[String, Object] = {
//    val client: BmlClient = createBMLClient(userName)
//    var resource: BmlDownloadResponse = null
//    if (version == null) {
//      resource = client.downloadResource(userName, resourceId)
//    } else {
//      resource = client.downloadResource(userName, resourceId, version)
//    }
//    if (!resource.isSuccess) throw new DSSErrorException(911115, "下载失败")
//    val map = new util.HashMap[String, Object]
//    map += "path" -> resource.fullFilePath
//    map += "is" -> resource.inputStream
//  }


//  private def createBMLClient(userName: String): BmlClient = {
//    if (userName == null)
//      BmlClientFactory.createBmlClient()
//    else
//      BmlClientFactory.createBmlClient(userName)
//  }
}
