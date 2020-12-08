package kw.kd.dss.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangfei
 * @version 1.0
 * @Description TODO
 * @create 2020-12-07 10:34
 */
public class HttpUtil {
    public static RestTemplate getRestClient(){
        CloseableHttpClient build =  HttpClientBuilder.create().useSystemProperties().build();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(build));
    }
}
