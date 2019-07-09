package net.ibytes.study;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Demo {

    public static void main(String[] args) throws UnsupportedEncodingException {

        final Base64.Encoder encoder = Base64.getEncoder();

        final String text = "123";
        final byte[] textByte = text.getBytes("UTF-8");
        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);


    }
}
