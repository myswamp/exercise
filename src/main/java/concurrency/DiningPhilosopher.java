package concurrency;

import java.util.concurrent.locks.ReentrantLock;


class Chopstick {

    private final int serialNo;
    private final ReentrantLock lock = new ReentrantLock();

    public Chopstick(int serialNo) {
        this.serialNo = serialNo;
    }

    public boolean use(int philosopherId) throws InterruptedException {
        boolean acquired = lock.tryLock();
        if (acquired) {
            System.out.printf("philosopher %d acquired chopstick %d%n", philosopherId, serialNo);
        } else {
            System.out.printf("philosopher %d failed to acquire chopstick %d%n", philosopherId, serialNo);
        }

        return acquired;
    }

    public void release(int philosopherId) {
        lock.unlock();
        System.out.printf("philosopher %d released chopstick %d%n", philosopherId, serialNo);
    }

}


public class DiningPhilosopher implements Runnable {
    int id;
    Chopstick left, right;
    int retryCount = 0;

    public DiningPhilosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        boolean hadMeal = false;
        while (!hadMeal) {
            try {
                Thread.sleep((retryCount++) * 1000 * id);
                System.out.printf("Philosopher %d - %d attempt %n", id, retryCount);
                boolean acquiredLeft = left.use(id);
                boolean acquiredRight = right.use(id);
                if (acquiredLeft && acquiredRight) {
                    Thread.sleep(1000);
                    System.out.printf("Philosopher %d had his meal%n", id);
                    hadMeal = true;
                } else {
                    System.out.printf("Philosopher %d did not have his meal%n", id);
                }
                if (acquiredLeft) {
                    left.release(id);
                }
                if (acquiredRight) {
                    right.release(id);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public static void main(String[] args) {
        Chopstick[] chopsticks = new Chopstick[5];
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        for (int i = 0; i < 5; i++) {
            int right = i == 0 ? 4 : i - 1;
            new Thread(new DiningPhilosopher(i, chopsticks[i], chopsticks[right])).start();
        }
    }
}
