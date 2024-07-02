package designpattern.strategy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    private static final String relativeDirectoryPath = "./src/main/java/designpattern/strategy/impl";
    private static final String packageName = "designpattern.strategy.impl";
    static Map<String, Strategy> strategies = new HashMap<>();

    static {
        Path path = Paths.get(relativeDirectoryPath).toAbsolutePath().normalize();

        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);

        try {
            List<String> fileNames = Files.list(path).filter(Files::isRegularFile).map(p -> {
                String fileName = p.getFileName().toString();
                return fileName.substring(0, fileName.lastIndexOf('.'));
            }).toList();


            for (String fileName : fileNames) {
                Class<?> theClass = Class.forName(packageName + "." + fileName);
                strategies.put(fileName, (Strategy) theClass.getDeclaredConstructor().newInstance());
            }

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        FooRequest fooRequest = new FooRequest("1", "FooStrategy", "Hello Foo");
        strategies.get(fooRequest.getType()).handle(fooRequest);

        BarRequest barRequest = new BarRequest("2", "BarStrategy", "Hello Bar");
        strategies.get(barRequest.getType()).handle(barRequest);

    }


}



