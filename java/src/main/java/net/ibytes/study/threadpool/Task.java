package net.ibytes.study.threadpool;

/**
 * @author dingfurong
 * @date 2019/3/27
 * @description
 */
public class Task {
    int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "TaskVo{" +
                "taskId=" + taskId +
                '}';
    }

}
