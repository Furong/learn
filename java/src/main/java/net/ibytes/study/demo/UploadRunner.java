package net.ibytes.study.demo;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author jianhonghu
 * @date 2019/8/19
 */
public class UploadRunner implements Runnable {

    private String ip;
    private String port;
    private Queue<UploadTask> queue;

    private String token;

    private volatile boolean isStop;

    public UploadRunner(String ip, String port, Queue<UploadTask> queue) {
        this.ip = ip;
        this.port = port;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isStop) {
            try {
                UploadTask task = queue.poll();
                if(task == null) {
                    continue;
                }
                upload(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void upload(UploadTask task) {
        MessageBO messageBO = task.getMessageBO();
        CompletableFuture<MessageBO> future = task.getFuture();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageBO.setMsg(UUID.randomUUID().toString());
        future.complete(messageBO);
    }


}
