package com.example.demo;

import java.util.HashSet;

/**
 * 同一台机器同一个业务，并发量3000的情况下会产生大量相同的id。
 * 这又是什么原因导致的呢。算法是没有问题的。

	问题原因
		仔细观察源码我们发现，代码里面生成id是使用的是syncronized关键字来进行同步的，syncronized使用在方法上表示的是锁住的是当前对象。而我们测试时使用，
		每一个线程对新建了一个对象，也就是说在生成id时，每个线程的取时间戳可以同时进行，序列sequence 自增也是的。所以才会可能产生相同的id。
	问题改进或者使用注意事项
		使用时，应该采用单例模式。也就是在多个线程去生成id的时候，用的需要时同一个SnowflakeIdWorker 对象，因为syncronized关键字用在方法上锁住的仅仅是当前的对象。
		因此采用单例就不会产生多线程安全问题。在实际的应用中可以考虑把SnowflakeIdWorker 对象使用spring来创建，scope使用singleton。
 * @author Administrator
 *
 */

public class SnowflakeIdWorkerTest2 {
	public static HashSet<Long> idSet = new HashSet<>();

    public static void main(String[] args) {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 0);
        for (long i = 0; i < 1000; i++) {
//            new Thread(new Worker(new SnowflakeIdWorker(1, 0))).start();
            new Thread(new Worker(snowflakeIdWorker)).start();
        }
    }

    static class Worker implements Runnable {

        private SnowflakeIdWorker snowflakeIdWorker;

        public Worker(SnowflakeIdWorker snowflakeIdWorker) {
            this.snowflakeIdWorker = snowflakeIdWorker;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                Long id = snowflakeIdWorker.nextId();
                System.out.println("id:"+id);
//                synchronized (SnowflakeIdWorkerTest.class) {
                    if (!idSet.add(id)) {
                        System.err.println("存在重复id:" + id);
                    }
//                }
            }
        }
    }
}
