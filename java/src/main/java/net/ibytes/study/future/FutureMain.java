package net.ibytes.study.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class FutureMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TaskExecutor executor = TaskExecutor.getInstance();
        List<Future<Integer>> futures = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            futures.add(executor.asyncRunTask(i + ""));
        }
        // to cancel task
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < 10;i ++) {
                    executor.cancleTask(i + "");
                }
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            if (futures.get(i).isCancelled()) {
                System.out.println("task " + i + " has been cancelled");
            } else {
                try {
                    System.out.println(futures.get(i).get());
                } catch (CancellationException e) {
                    System.out.println("task " + i + " has been cancelled");
                }
            }
            executor.remove(i + "");
        }
        executor.shutdown();
    }
}
