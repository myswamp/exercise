package misc;

public class Singleton {

    private Singleton() {
        System.out.println("constructor");
    }

    public static Singleton getInstance() {
        System.out.println("get instance");
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        static Singleton instance = new Singleton();
    }
}
