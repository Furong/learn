package net.ibytes.study.future;

import java.util.concurrent.TimeUnit;

public class CountTask {
    private String taskId;

    public CountTask(String taskId) {
        this.taskId = taskId;
    }

    //计算分数
    public int count(String taskId) {
        try {
            // to mock long time task
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        //执行一个长时间的任务
        return Integer.parseInt(taskId);
    }
}