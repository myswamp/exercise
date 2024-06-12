package concurrency;

import java.util.concurrent.Semaphore;

class Rice {
    private int bowlOfRice;

    public Rice(int bowlOfRice) {
        this.bowlOfRice = bowlOfRice;
    }

    public synchronized void eat() throws InterruptedException {
        bowlOfRice--;
        Thread.sleep(1000);
    }

    public synchronized int getBowlOfRice() {
        return bowlOfRice;
    }
}

public class DiningPhilosopher implements Runnable {
    int id;
    Semaphore chopsticks;
    Rice rice;

    public DiningPhilosopher(int id, Semaphore chopsticks, Rice rice) {
        this.id = id;
        this.chopsticks = chopsticks;
        this.rice = rice;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (rice.getBowlOfRice() < 1) {
                    System.out.println(String.format("No more rice to eat for philosopher %d", this.id));
                    break;
                }
                if (chopsticks[id].tryAcquire()) {
                    if (chopsticks[(id + 1) % chopsticks.length].tryAcquire()) {
                        if (rice.getBowlOfRice() < 1) {
                            System.out.println(String.format("No more rice to eat for philosopher %d", this.id));
                            chopsticks[id].release();
                            chopsticks[(id + 1) % chopsticks.length].release();
                            break;
                        }
                        rice.eat();
                        System.out.println(String.format("Philosopher %d ate one bowl of rice, remaining rice %d", id, rice.getBowlOfRice()));
                        chopsticks[id].release();
                        chopsticks[(id + 1) % chopsticks.length].release();
                        Thread.sleep(100);
                    } else {
                        chopsticks[id].release();
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void main(String[] args) {
        int philosopherCount = 5;
        Semaphore[] chopsticks = new Semaphore[philosopherCount];
        for (int i = 0; i < philosopherCount; i++) {
            chopsticks[i] = new Semaphore(1);
        }
        Rice rices = new Rice(15);
        for (int i = 0; i < philosopherCount; i++) {
            new Thread(new DiningPhilosopher(i, chopsticks, rices)).start();
        }
    }


}
