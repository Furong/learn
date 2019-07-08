package net.ibytes.study.redis.demo1;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author dingfurong
 * @date 2019/7/8
 * @description 从第三方redis获取数据
 */
public class ReceiveMessage {

    public static void main(String[] args){

        //创建redis客户端
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //密码
        jedis.auth("123");
        //连接
        jedis.connect();

        while(true){
            consume(jedis);
        }


    }

    private static void consume(Jedis jedis){
        //移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
        //List中的元素总是偶数个，其中奇数表示的从哪一个队列中监听到的消息，偶数表示消息的内容
        List<String> messages = jedis.brpop(0,"redis_send_message");
        if(messages.size() > 0){
            String keyName = messages.get(0);
            String message = messages.get(1);
            System.out.print( keyName + "\t" + message + "\t");
            Message messageObject = JSON.parseObject(message,Message.class);
            System.out.println(messageObject);
        }
    }

}
