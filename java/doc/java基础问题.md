## 1. JAVA抽象类中的构造方法

抽象类中的构造方法
```java
//抽象父类
public abstract class AbstractFather {
    protected Resume resume;

    protected AbstractFather(Resume resume){
        this.resume = resume;
    }

    protected void makeMoney(){
        System.out.println(resume.getName());
    }
}
//子类
public class Son extends AbstractFather {

    public Son(Resume resume) {
        super(resume);
    }
    
    public static void main(String[] args){
        Resume resume = new Resume();
        resume.setName("test");
        Son son = new Son(resume);
        son.makeMoney();
    }
}
```

抽象类中的构造方法和普通类的构造方法一样，都是用来初始化类的；只是抽象类的构造方法不能直接调用，因为抽象类不能被实例化。

## 2. JAVA中的守护线程

在Java中有两类线程：用户线程 (User Thread)、守护线程 (Daemon Thread)。 

所谓守护 线程，是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。

将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现

