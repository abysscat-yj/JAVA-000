package homework;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WhileSleepWork {

    static volatile long fiboResult = -1;

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        // 单线程池异步执行任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> fiboResult = sum());

        while (fiboResult == -1) {
            Thread.sleep(10);
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + fiboResult);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        executorService.shutdown();
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
