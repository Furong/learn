package net.ibytes.study.redis.demo2;

import redis.clients.jedis.JedisPubSub;

/**
 * @author dingfurong
 * @date 2019/7/9
 * @description
 */
public class MessageSub extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(message);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }
}
