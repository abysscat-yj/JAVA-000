package homework;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockWork {

    static volatile long fiboResult = -1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();


        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        try {
            lock.lock();

            // 单线程池异步执行任务
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    lock.lock();
                    fiboResult = sum();
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            });

            condition.await();

            // 然后退出main线程
            executorService.shutdown();

        } finally {
            lock.unlock();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + fiboResult);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }


    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 1) {
            return 0;
        } else if (a == 1) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }



}
