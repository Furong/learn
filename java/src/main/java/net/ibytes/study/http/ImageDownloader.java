package net.ibytes.study.http;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author dingfurong
 * @date 2019/10/11
 * @description 下载图片
 */
public class ImageDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDownloader.class);

    private CloseableHttpClient httpClient;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

    public boolean downloadImage(String imgUrl,String filePath){
        initHttpClient();
        boolean flag = fetchContent(imgUrl,filePath);
        destroyHttpClient();
        return flag;
    }

    /**
     * 初始化HttpClient
     */
    private void initHttpClient(){
        httpClient = HttpClients.custom().setUserAgent(USER_AGENT).build();
    }

    private boolean fetchContent(String imgUrl,String filePath){
        HttpGet httpGet = new HttpGet(imgUrl);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                FileUtils.copyToFile(inputStream,new File(filePath));
                return true;
            }else{
                LOGGER.error("下载图片失败,http错误码 {}",statusCode);
            }
        } catch (IOException e) {
            LOGGER.error("获取CloseableHttpResponse失败",e);
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                LOGGER.error("关闭CloseableHttpResponse失败",e);
            }
        }
        return false;
    }

    private void destroyHttpClient(){
        if(httpClient != null){
            try {
                httpClient.close();
            } catch (IOException e) {
               LOGGER.error("关闭HttpClient失败",e);
            }
        }
    }

    public static void main(String[] args){
        ImageDownloader imageDownloader = new ImageDownloader();
        String imgUrl = "http://b.hiphotos.baidu.com/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg";
        String pathUrl = "image.png";
        System.out.println(imageDownloader.downloadImage(imgUrl,pathUrl));
    }


}
