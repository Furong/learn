## Java中的线程池

### ThreadPoolExecutor使用

```java
public class TaskVo {
        int taskId;

        public TaskVo(int taskId) {
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


public class Task implements Runnable
{  
    private String name;  
   
    public Task(String name)   
    {  
        this.name = name;  
    }  
       
    public String getName() {  
        return name;  
    }  
   
    @Override  
    public void run()   
    {  
        try  
        {  
            Long duration = (long) (Math.random() * 10);  
            System.out.println("Doing a task during : " + name);  
            TimeUnit.SECONDS.sleep(duration);
        }   
        catch (InterruptedException e)   
        {  
            e.printStackTrace();  
        }  
    }  
}  

public class ThreadPool {
    private static AtomicInteger NUM = new AtomicInteger(1);
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(8, 8,
            0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            String name = "Register-thread-" + NUM.getAndIncrement();
            System.out.println(name);
            Thread t = new Thread(r, name);
            return t;
        }
    });

    public static void main(String[] args){
        for(int i =1; i < 10 ; i++){
            Task task = new Task("task"+ i);
            System.out.println("add task " + i);
            TaskVo taskVo = new TaskVo(i);
            EXECUTOR.submit(task);
            EXECUTOR.submit(new Runnable() {
                @Override
                public void run() {
                   System.out.println(taskVo.toString());
                }
            });
        }
        EXECUTOR.shutdown();
    }
}


```

