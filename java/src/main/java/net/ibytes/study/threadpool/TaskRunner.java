package net.ibytes.study.threadpool;

import java.util.concurrent.TimeUnit;

/**
 * @author dingfurong
 * @date 2019/3/27
 * @description
 */
public class TaskRunner implements Runnable {
    private String name;

    public TaskRunner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            Long duration = (long) (Math.random() * 10);
            System.out.println("Doing  taskRunner  : " + name);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
