package net.ibytes.study.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author jianhonghu
 * @date 2019/8/19
 */
public class UploadTask {
    private MessageBO messageBO;
    private CompletableFuture<MessageBO> future;

    public UploadTask(MessageBO messageBO, CompletableFuture<MessageBO> future) {
        this.messageBO = messageBO;
        this.future = future;
    }

    public MessageBO getMessageBO() {
        return messageBO;
    }

    public void setMessageBO(MessageBO messageBO) {
        this.messageBO = messageBO;
    }

    public CompletableFuture<MessageBO> getFuture() {
        return future;
    }

    public void setFuture(CompletableFuture<MessageBO> future) {
        this.future = future;
    }
}
