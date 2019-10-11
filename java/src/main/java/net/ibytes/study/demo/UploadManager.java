package net.ibytes.study.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author jianhonghu
 * @date 2019/8/19
 */
public class UploadManager {
    private ExecutorService executorService;

    private BlockingQueue<UploadTask> queue = new LinkedBlockingQueue<>();

    private  String ip = "127.0.0.1";
    private  String port = "8080";

    private UploadRunner[] runners;

    public UploadManager(String ip,String port) {
        this.executorService = Executors.newFixedThreadPool(10);
        runners = new UploadRunner[10];
        for (int i = 0; i < 10; i++) {
            runners[i] = new UploadRunner(ip, port, queue);
            executorService.execute(runners[i]);
        }
    }

    public CompletableFuture<MessageBO> offer(MessageBO target) {
        CompletableFuture<MessageBO> future = new CompletableFuture<>();
        // offer task
        queue.offer(new UploadTask(target, future));
        System.out.println(queue.size());
        return future;
    }

    public void update(String ip,String port) {
        this.ip = ip;
        this.port = port;
        for (int i = 0; i < 10; i++) {
            runners[i].stop();
        }
        for (int i = 0; i < 10; i++) {
            runners[i] = new UploadRunner(ip, port, queue);
            executorService.execute(runners[i]);
        }
    }


    public static void main(String[] args) throws IOException {
        String ip ="";
        String port = "";
        UploadManager demoServer = new UploadManager(ip,port);
        List<MessageBO> list = generate();

        for (MessageBO messageBO : list) {

            CompletableFuture<MessageBO> future = demoServer.offer(messageBO);
            future.whenComplete((r, ex) -> {
                if (ex != null) {
                    // TODO 有异常，处理上传失败问题
                    ex.printStackTrace();
                } else {
                    // 上传成功，r就是上传任务返回的结果url

                    MessageBO result = r;
                    // TODO 做相应的处理，比如给客户端返回上传结果等等
                    System.out.println("oldvalue " + result.getOldMsg() + "new value" + result.getMsg());

                }
            });
        }
    }

    public static List<MessageBO> generate() {
        List<MessageBO> list = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            MessageBO messageBO = new MessageBO();
            messageBO.setOldMsg(String.valueOf(i));
            list.add(messageBO);
        }

        return list;
    }


}
