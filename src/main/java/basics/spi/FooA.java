package basics.spi;

public class FooA implements Foo {
    @Override
    public void echo() {
        System.out.println("I am FOO A");
    }
}
