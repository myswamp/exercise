package basics.spi;

public class FooB implements Foo {
    @Override
    public void echo() {
        System.out.println("I am FOO B");
    }
}
