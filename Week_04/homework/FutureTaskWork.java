package homework;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskWork {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        // 单线程池异步执行任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<Long> futureTask = new FutureTask<>(() -> Long.valueOf(sum()));

        executorService.execute(futureTask);

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + futureTask.get());

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
