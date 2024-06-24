package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLock {

    public static void main(String[] args) {

        Object mutex1 = new Object();
        Object mutex2 = new Object();

        ExecutorService es = Executors.newFixedThreadPool(2);

        es.submit(() -> {
            synchronized (mutex1) {
                System.out.println(String.format("%s acquired mutex 1", Thread.currentThread().getName()));
                synchronized (mutex2) {
                    System.out.println(String.format("%s acquired mutex 2", Thread.currentThread().getName()));
                }
            }
        });

        es.submit(() -> {
            synchronized (mutex2) {
                System.out.println(String.format("%s acquired mutex 2", Thread.currentThread().getName()));
                synchronized (mutex1) {
                    System.out.println(String.format("%s acquired mutex 1", Thread.currentThread().getName()));
                }
            }
        });


    }
}
