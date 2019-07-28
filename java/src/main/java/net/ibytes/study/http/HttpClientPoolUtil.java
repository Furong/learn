package net.ibytes.study.http;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author dingfurong
 * @date 2019/7/19
 * @description httpclient连接池
 */
public class HttpClientPoolUtil {

    /**
     * 设置连接建立的超时时间为10s
     */
    private static final int CONNECT_TIMEOUT = 10;
    /**
     *
     */
    private static final int SOCKET_TIMEOUT = 10;
    /**
     * 最大连接数
     */
    private static final int MAX_CONN = 12;
    private static final int Max_PRE_ROUTE = 12;
    private static final int MAX_ROUTE = 12;

    /**
     *  发送请求的客户端单例
     */
   // private static CloseableHttpClient httpClient;
    /**
     * 连接池管理类
     */
   // private static PoolingHttpClientConnectionManager manager;


    private static ScheduledExecutorService monitorExecutor;

    /**
     *  相当于线程锁,用于线程安全
     */
    private final static Object syncLock = new Object();
    



}
