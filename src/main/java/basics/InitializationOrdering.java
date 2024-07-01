package basics;

public class InitializationOrdering {
    static class Test {
        int a = 0;

        Test() {
            System.out.println("constructor");
            a = 2;
            System.out.println(a);
        }

        static {
            System.out.println("static block");
        }

        {
            System.out.println(a);
            a = 1;
            System.out.println("non-static block");
            System.out.println(a);
        }
    }


    public static void main(String[] args) {
        Test test = new Test();
    }
}
