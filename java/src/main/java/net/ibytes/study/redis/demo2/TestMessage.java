package net.ibytes.study.redis.demo2;

import redis.clients.jedis.Jedis;

/**
 * @author dingfurong
 * @date 2019/7/9
 * @description
 */
public class TestMessage {

    public static void main(String[] args){
        //创建redis客户端
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //密码
        jedis.auth("123");
        //连接
        jedis.connect();

        jedis.subscribe(new MessageSub(),"message_pub");

        jedis.disconnect();

    }
}
