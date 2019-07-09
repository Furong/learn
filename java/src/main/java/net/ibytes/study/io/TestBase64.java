package net.ibytes.study.io;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author dingfurong
 * @date 2019/7/9
 * @description Java8 Base64加密与揭秘
 */
public class TestBase64 {

    public static void main(String[] args){
        String txt = "123456";
        Base64.Encoder encoder = Base64.getEncoder();
        String encodingTxt = encoder.encodeToString(txt.getBytes(StandardCharsets.UTF_8));

        Base64.Decoder decoder = Base64.getDecoder();
        String decodingTxt = new String(decoder.decode(encodingTxt),StandardCharsets.UTF_8);
        if(txt.equals(decodingTxt)){
            System.out.println("加密解密成功");
        }
    }
}
