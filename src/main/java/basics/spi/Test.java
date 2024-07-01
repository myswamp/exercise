package basics.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Test {
    public static void main(String[] args) {
        ServiceLoader<Foo> loader = ServiceLoader.load(Foo.class);

        Iterator<Foo> iterator = loader.iterator();

        while(iterator.hasNext()) {
            Foo next = iterator.next();
            next.echo();
        }
    }
}
