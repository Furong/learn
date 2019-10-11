package net.ibytes.study.demo;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jianhonghu
 * @date 2019/8/19
 */
public class UploadRunner implements Runnable {

    private volatile String ip;
    private volatile String port;
    private BlockingQueue<UploadTask> queue;

    private String token;

    private volatile boolean isStop;

    private WritePic writePic;


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();



    public UploadRunner(String ip, String port, BlockingQueue<UploadTask> queue) {
        this.ip = ip;
        this.port = port;
        this.queue = queue;
    }


    public void update(String ip, String port) {
        //
        writeLock.lock();
        try {
            //

        } finally {
            writeLock.unlock();
        }
        // create a new connnection
    }


    private int dequeueTimeout = 1000;

    @Override
    public void run() {
        while (!isStop) {
            try {
                //
                UploadTask task = null;
                try {
                    //
                    task = queue.poll(dequeueTimeout, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    break;
                }
                if (task == null) {
                    continue;
                }
                readLock.lock();
                try {
                    upload(task);
                } finally {
                    readLock.unlock();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.isStop = true;
    }

    private void upload(UploadTask task) {
        MessageBO messageBO = task.getMessageBO();
        CompletableFuture<MessageBO> future = task.getFuture();

        writePic.upload();

        try {
            // TODO

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageBO.setMsg(UUID.randomUUID().toString());
        future.complete(messageBO);
    }


}
