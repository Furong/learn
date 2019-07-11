package net.ibytes.study.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dingfurong
 * @date 2019/7/10
 * @description JAVA正则表达式
 */
public class PatternTest {

    public static void main(String[] args){
        //ip地址
        String ipReg = "^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$";
        Pattern ipPattern = Pattern.compile(ipReg);

        boolean flag = ipPattern.matcher("111.11.11").matches();
        System.out.println(flag);
        flag = ipPattern.matcher("256.11.11.255").matches();
        System.out.println(flag);

        //端口号
        String reg = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])";
        Pattern pattern = Pattern.compile(reg);

        boolean flag1 = pattern.matcher("655361").matches();
        System.out.println("flag1" + flag1);


        //不包含特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        regEx = "[`~!@#$%^&*<>?:\"{},;//'\\[\\].!#￥（——）：；“”‘、，|《》？。【】]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("/");
        System.out.println("特殊字符" + m.find());


    }
}
