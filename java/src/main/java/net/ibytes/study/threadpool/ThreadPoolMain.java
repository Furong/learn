package net.ibytes.study.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dingfurong
 * @date 2019/3/27
 * @description
 */
public class ThreadPoolMain {
    private static AtomicInteger NUM = new AtomicInteger(1);
    private static ExecutorService EXECUTOR = new ThreadPoolExecutor(8, 8, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            String name = "Register-thread-" + NUM.getAndIncrement();
            System.out.println(name);
            Thread t = new Thread(r, name);
            return t;
        }
    });

    public static void main(String[] args){
        for(int i = 0; i < 10; i++){
            Task task = new Task(i);
            //提交任务
            EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("doing task " + task.getTaskId());
                }
            });

            TaskRunner taskRunner = new TaskRunner(i + " ");
            EXECUTOR.execute(taskRunner);
            //EXECUTOR.execute((i) -> System.out.println("running task " +i));
        }
        EXECUTOR.shutdown();

        //TODO 使用google guava包的 ThreadFactoryBuilder
    }
}
