package string;

import java.util.Arrays;

public class StringPlay {

    public static void main(String[] args) {
        String s = "*.111.*";

        String[] parts = s.split("\\*");
        System.out.println(parts.length);

        // System.out.print(parts[2]);

        Arrays.stream(parts).forEach(System.out::println);
    }
}
