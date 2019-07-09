package net.ibytes.study.redis.demo2;

import com.alibaba.fastjson.JSON;
import net.ibytes.study.redis.demo1.Message;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dingfurong
 * @date 2019/7/9
 * @description
 */
public class MessagePub {

    public static void main(String[] args){
        //创建redis客户端
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //密码
        jedis.auth("123");
        //连接
        jedis.connect();
        //插入数据
        pubMessages(jedis,1);
        //关闭连接
        jedis.connect();
    }

    private static void pubMessages(Jedis jedis, int frequency) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.setMessageId(UUID.randomUUID().toString());
                message.setBody("uuid is " + UUID.randomUUID());
                jedis.publish("message_pub", JSON.toJSONString(message));
            }
        },0,frequency, TimeUnit.SECONDS);

    }
}
