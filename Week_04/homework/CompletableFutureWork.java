package homework;


import java.util.concurrent.CompletableFuture;

public class CompletableFutureWork {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        // 异步执行任务

        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> Long.valueOf(sum()));

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + future.get());

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
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
