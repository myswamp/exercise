package recursion;

import java.util.*;

public class PhoneNumberMnemonics {
    static Map<Integer, String[]> map = Map.of(
            1, new String[]{"1"},
            2, new String[]{"a", "b", "c"},
            3, new String[]{"d", "e", "f"},
            4, new String[]{"g", "h", "i"},
            5, new String[]{"j", "k", "l"},
            6, new String[]{"m", "n", "o"},
            7, new String[]{"p", "q", "r", "s"},
            8, new String[]{"t", "u", "v"},
            9, new String[]{"w", "x", "y", "z"},
            0, new String[]{"0"}
    );


    static String[] cartesianProduct(String[] first, String[] second) {
       String[] product = new String[first.length * second.length];
       int idx = 0;
        for (String c1 : first) {
            for (String c2 : second) {
                product[idx++] = c1 + c2;
            }
        }
        return product;
    }

    static String[] getMnemonics(String phoneNumber) {
        String[] mnemonics = {""};

        for(char c: phoneNumber.toCharArray()) {
            String[] combo = map.get(c - '0');
            mnemonics = cartesianProduct(mnemonics, combo);
        }
        return mnemonics;
    }

    public static void main(String[] args) {
        System.out.println("Enter a phone number");
        Scanner scanner = new Scanner(System.in);
        String phoneNumber = scanner.nextLine();
        scanner.close();
        System.out.println("The possible mnemonics are: ");
        System.out.println(Arrays.toString(getMnemonics(phoneNumber)));
    }
}
