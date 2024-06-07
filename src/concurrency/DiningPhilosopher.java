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
                chopsticks.acquire(2);
                if (rice.getBowlOfRice() < 1) {
                    System.out.println(String.format("no more rice to eat for philosopher %d", this.id));
                    chopsticks.release(2);
                    break;
                }
                rice.eat();
                System.out.println(String.format("Philosopher %d ate one bowl of rice, remaining rices %d", id, rice.getBowlOfRice()));
                chopsticks.release(2);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void main(String[] args) {
        Semaphore chopsticks = new Semaphore(5);
        Rice rices = new Rice(15);
        for (int i = 1; i < 6; i++) {
            new Thread(new DiningPhilosopher(i, chopsticks, rices)).start();
        }
    }


}
