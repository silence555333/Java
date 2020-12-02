package kw.kd.dss.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-11-30 17:35
 * 解析DSS的json文件
 * 注意：文件中存放的并不是一个json,而是会存在多个json拼接
 */
public class FlowParserUtil {
    private static final Logger logger = LoggerFactory.getLogger(FlowParserUtil.class);

    public List<String> getFlowResouceIdList(String jsonPath,String localFile){

        //先将hdfs上的json文件下载到本地
        HDFSUtil.downloadHDFS2Localfile(jsonPath,localFile);
        //解析本地json文件
        List<String> list_resource=parseJsonFile(localFile);
        //转换去重
        Set<String> set_resource=new HashSet<>(list_resource);
        List<String> resourceList = new ArrayList<>(set_resource);
        return resourceList;
    }

    public List<String> parseJsonFile(String fileName){
        List<String> list=new ArrayList<>();
        //解析本地json文件
        String s = FileUtil.readJsonFile(fileName);
        //这里需要判断是否需要分开处理}{ 为分隔符,进行一个完整json的解析

        if(checkjsonlist(s,"}{")){
            String[] list_json=s.replace("}{","}&DSS-LINIKS&{").split("&DSS-LINIKS&");
            for (String json:list_json){
                JSONObject jobj = JSON.parseObject(json);
                List<String> list_tmp=parseJsonObject(jobj);
                list.addAll(list_tmp);
            }
        }else {
            JSONObject jobj = JSON.parseObject(s);
            list = parseJsonObject(jobj);
        }
        return list;
    }

    public List<String> parseJsonObject(JSONObject jsonObject){
        List<String> list=new ArrayList<>();
        JSONArray nodeslist = jsonObject.getJSONArray("nodes");
        for (int i = 0 ; i < nodeslist.size();i++ ){
            JSONObject node= (JSONObject) nodeslist.get(i);
            JSONArray resourcesList=node.getJSONArray("resources");

            if(resourcesList.isEmpty()) {
                logger.info(String.format("解析的资源文件中node resource 为空"));
            }else {
                for (int j=0;j<resourcesList.size();j++){
                    JSONObject resources = (JSONObject)resourcesList.get(j);
                    String resource= (String) resources.get("resourceId");
                    if(resource==null||resource.equals("")||resource.length()==0){

                    }else {
                        list.add(resource);
                    }

                }
            }
        }
        return list;
    }
    public boolean checkjsonlist(String jsonString,String filterString){

        if(jsonString.contains(filterString)){
            return true;
        }

        return false;
    }
}
