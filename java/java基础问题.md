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