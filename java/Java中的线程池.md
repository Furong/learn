## Java中的线程池

### ThreadPoolExecutor使用

```java
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
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for(int i =1; i < 10 ; i++){
            Task task = new Task("task"+ i);
            System.out.println("add task " + i);
            EXECUTOR.submit(task);
            //executor.submit(task);
        }
    }

}

```

