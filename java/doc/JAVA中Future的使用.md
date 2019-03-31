# JAVA中Future的使用

功能描述：任务有唯一标识任务号，如果任务正在执行，不能提交任务；可以取消任务

### FutureTask详解

Future接口和实现Future接口的FutureTask类，代表异步计算的结果，位于concurrent包下。

FutureTask可以交给Executor执行，也可以由调用线程直接执行(FutureTask.run())。根据FutureTask.run()方法被执行的时机，FutureTask可以处于下面3种状态。

1) 未启动。FutureTask.run()方法还没有执行之前，FutureTask处于未启动状态。当创建一个FutureTask，且没有执行FutureTask.run()方法之前，这个FutureTask处于未启动状态。

2)已启动。FutureTask.run()方法被执行的过程中，FutureTask处于已启动状态。

3)已完成。FutureTask.run()方法执行完成后正常结束，或被取消或执行FutureTask.run()方法时抛出异常而异常结束，FutureTask处于已完成状态。

![](D:\个人发展\每日学习\img\Future.png)

当FutureTask处于未启动或已启动状态时，执行FutureTask.get()方法将导致调用线程阻塞；当FutureTask处于已完成状态时，执行FutureTask.get()方法将导致调用线程立即返回结果或抛出异常。

当FutureTask处于未启动状态时，执行FutureTask.cancel()方法将导致此任务永远不会被执行；当FutureTask处于已启动状态时，执行FutureTask.cancel(true)方法将以中断执行此任务线程的方式来试图停止任务；当FutureTask处于已启动状态时，执行FutureTask.cancel(false)方法将不会对正在执行此任务的线程产生影响(让正在执行的任务运行完成)；当FutureTask处于已经完成状态时，执行FutureTask.cancel(...)方法将返回false。

![](D:\个人发展\每日学习\img\get.png)



当任务被取消时，调用FutureTask.get() 出现CancellationException

### 参考资料

《Java并发编程的艺术》方腾飞 魏鹏 程晓明 著.