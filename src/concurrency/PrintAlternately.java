package concurrency;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counted {
    protected int count = 5;
}

class Foo extends Counted implements Runnable {
    private Object lock;

    public Foo(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        synchronized (lock) {
            while (true) {
                lock.notifyAll();
                System.out.println("foo");
                count--;
                try {
                    if (count == 0) {
                        break;
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}

class Bar extends Counted implements Runnable {

    private Object lock;

    public Bar(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (true) {
                lock.notifyAll();
                System.out.println("bar");
                count--;
                try {
                    if (count == 0) {
                        break;
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}

class AnotherCounted extends Counted {
    protected Lock lock;
    protected Condition condition;

    protected AnotherCounted(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }
}

class AnotherFoo extends AnotherCounted implements Runnable {

    public AnotherFoo(Lock lock, Condition condition) {
        super(lock, condition);
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (true) {
                condition.signalAll();
                System.out.println("another foo");
                count--;

                if (count == 0) {
                    break;
                }
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}

class AnotherBar extends AnotherCounted implements Runnable {

    public AnotherBar(Lock lock, Condition condition) {
        super(lock, condition);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                condition.signalAll();
                System.out.println("another bar");
                count--;
                if (count == 0) {
                    break;
                }

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                lock.unlock();
            }

        }
    }
}

public class PrintAlternately {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(new Foo(lock));
        Thread t2 = new Thread(new Bar(lock));

        t1.start();
        t2.start();

        Thread.sleep(3000);
        System.out.print(System.lineSeparator());

        Lock anotherLock = new ReentrantLock();
        Condition condition = anotherLock.newCondition();

        Thread t3 = new Thread(new AnotherFoo(anotherLock, condition));
        Thread t4 = new Thread(new AnotherBar(anotherLock, condition));

        t3.start();
        t4.start();

    }

}
