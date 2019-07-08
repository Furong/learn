package net.ibytes.study.redis.demo1;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dingfurong
 * @date 2019/7/8
 * @description 模拟A系统，向redis插入数据 向List中插入数据
 */
public class SendMessage {

    public static void main(String[] args){
        //创建redis客户端
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //密码
        jedis.auth("123");
        //连接
        jedis.connect();
        //插入数据
        generateMessages(jedis,1);
        //关闭连接
        jedis.connect();
    }


    /**
     * 按照频率产生消息
     * @param jedis
     * @param frequency
     */
    public static void generateMessages(Jedis jedis, int frequency){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            jedis.rpush("redis_send_message",genertateMessage());
        },0, frequency,TimeUnit.SECONDS);
    }


    private static String genertateMessage(){
        Message message = new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setBody("uuid is " + UUID.randomUUID());
        return JSON.toJSONString(message);
    }
}
