# JUC

## 1、预备

pom.xml依赖如下:

```xml
 <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.11</version>
        </dependency>
  </dependencies>
```





## 2、进程与线程

### 2.1、进程与进程

#### 进程

- 程序由指令和数据组成，但是这些指令要运行，数据要读写，就必须将指令加载到cpu，数据加载至内存。在指令运行过程中还需要用到磁盘，网络等设备，进程就是用来加载指令管理内存管理IO的
- 当一个指令被运行，从磁盘加载这个程序的代码到内存，这时候就开启了一个进程
- 进程就可以视为程序的一个实例，大部分程序都可以运行多个实例进程（例如记事本，浏览器等），部分只可以运行一个实例进程（例如360安全卫士）

#### 线程

- 一个进程之内可以分为一到多个线程。
- 一个线程就是一个指令流，将指令流中的一条条指令以一定的顺序交给 CPU 执行
- Java 中，线程作为最小调度单位，进程作为资源分配的最小单位。 在 windows 中进程是不活动的，只是作 为线程的容器（这里感觉要学了计算机组成原理之后会更有感觉吧！）

#### 二者对比

进程基本上相互独立的，而线程存在于进程内，是进程的一个子集 进程拥有共享的资源，如内存空间等，供其内部的线程共享 进程间通信较为复杂 同一台计算机的进程通信称为 IPC（Inter-process communication） 不同计算机之间的进程通信，需要通过网络，并遵守共同的协议，例如 HTTP 线程通信相对简单，因为它们共享进程内的内存，一个例子是多个线程可以访问同一个共享变量 线程更轻量，线程上下文切换成本一般上要比进程上下文切换低



### 2.2 并行与并发

#### 并发

在单核 cpu 下，线程实际还是串行执行的。操作系统中有一个组件叫做任务调度器，将 cpu 的时间片（windows 下时间片最小约为 15 毫秒）分给不同的程序使用，只是由于 cpu 在线程间（时间片很短）的切换非常快，人类感 觉是同时运行的 。一般会将这种线程轮流使用 CPU 的做法称为并发（concurrent）



#### 并行

多核 cpu下，每个核（core） 都可以调度运行线程，这时候线程可以是并行的，不同的线程同时使用不同的cpu在执行。



#### 二者对比

引用 Rob Pike 的一段描述：并发（concurrent）是同一时间应对（dealing with）多件事情的能力，并行（parallel）是同一时间动手做（doing）多件事情的能力

- 家庭主妇做饭、打扫卫生、给孩子喂奶，她一个人轮流交替做这多件事，这时就是并发
- 雇了3个保姆，一个专做饭、一个专打扫卫生、一个专喂奶，互不干扰，这时是并行
- 家庭主妇雇了个保姆，她们一起这些事，这时既有并发，也有并行（这时会产生竞争，例如锅只有一口，一 个人用锅时，另一个人就得等待）





### 2.3 应用

#### 同步和异步的概念

以调用方的角度讲，如果需要等待结果返回才能继续运行的话就是同步，如果不需要等待就是异步

#### 1) 设计

多线程可以使方法的执行变成异步的，比如说读取磁盘文件时，假设读取操作花费了5秒，如果没有线程的调度机制，这么cpu只能等5秒，啥都不能做。

#### 2) 结论

- 比如在项目中，视频文件需要转换格式等操作比较费时，这时开一个新线程处理视频转换，避免阻塞主线程
- tomcat 的异步 servlet 也是类似的目的，让用户线程处理耗时较长的操作，避免阻塞 tomcat 的工作线程
- ui 程序中，开线程进行其他操作，避免阻塞 ui 线程



## 3. java线程

### 3.1 创建和运行线程

#### 方法一，直接使用 Thread

```java
public static void main(String[] args) {
        // 创建一个线程
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                // 线程内要执行的方法
                log.debug("running...");
            }
        };
        // 给线程起个名字
        thread1.setName("t1");
        // 启动创建的线程
        thread1.start();

        // 执行主线程
        log.info("running...");
    }
```



#### 方法二，使用 Runnable 配合 Thread

把【线程】和【任务】（要执行的代码）分开，Thread 代表线程，Runnable 可运行的任务（线程要执行的代码）Test2.java

```java
   public static void main(String[] args) {
        Runnable runnable1 = new Runnable(){
            @Override
            public void run() {
                // 线程内要执行的方法
                log.debug("running");
            }
        };

        // 创建线程
        Thread thread = new Thread(runnable1);

        // 启动线程
        thread.start();

        // 主线程
        log.info("running");
    }
```



使用Lambda表达式的形式简化代码:

```java
// 形式1：   
public static void main(String[] args) {
        Runnable runnable1 = () -> {
            // 线程内要执行的方法
            log.debug("running");
        };

        // 创建线程
        Thread thread = new Thread(runnable1);

        // 启动线程
        thread.start();

        // 主线程
        log.info("running");
}

// 形式2:
public static void main(String[] args) {
        // 创建线程
        Thread thread = new Thread(() -> {
            log.debug("running");
        });

        // 启动线程
        thread.start();

        // 主线程
        log.info("running");
}
```



#### 小结

方法1 是把线程和任务合并在了一起，方法2 是把线程和任务分开了，用 Runnable 更容易与线程池等高级 API 配合，用 Runnable 让任务类脱离了 Thread 继承体系，更灵活。通过查看源码可以发现，方法二其实到底还是通过方法一执行的！



#### 方法三，FutureTask 配合 Thread

FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况 Test3.java

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            // 实现多线程的第三种方法可以返回数据
            @Override
            public Integer call() throws Exception {
                // 要执行的任务代码
                log.debug("running...");
                Thread.sleep(1000);
                return 100;
            }
        });

        Thread thread = new Thread(task,"t1");
        thread.start();

        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer integer = task.get();
        log.info(String.valueOf(integer));
    }
```



Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。

```java
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

Future提供了三种功能： 　　

1. 判断任务是否完成； 　　
2. 能够中断任务； 　　
3. 能够获取任务执行结果。



### 3.2 演示交替执行代码:

```java
    public static void main(String[] args) {
        new Thread(()-> {
            while (true) {
                log.debug("running1...");
            }
        },"t1").start();

        new Thread(()-> {
           while(true) {
               log.debug("running2...");
           }
        },"t2").start();
    }
```



### 3.3 查看线程进程的方法

#### Windows

- 任务管理器可以查看进程和线程数，也可以用来杀死进程和线程
- tasklist 查看进程
- tasklist | findstr <关键字> 筛选进程信息
- taskkill /P /PID <进程编号> 杀死进程



#### Linux

- ps -fe 查看所有进程
- ps -fT -p <PID> 查看某个进程(PID)的所有线程
- kill <PID>杀死进程
- top 按大写H切换是否显示进程
- top -H -p <PID> 查看某个进程(PID)的所有线程



#### Java

- jps 命令查看所有Java进程
- jstack <PID> 查看某个Java进程(PID)的所有线程状态
- jconsole 来查看某个Java进程中线程的运行情况(图形化界面)
