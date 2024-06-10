package hashmap;

import java.util.*;

public class PairOfSocks {

    public static void main(String[] args) {

        String[] socks = new String[]{"red,left", "red,left", "yellow,right", "yellow,right", "blue,right",
                "red,right", "red,right", "red,right"};

        Map<String, Integer> map = new HashMap<>();
        List<String> listOfPairs = new ArrayList<>();

        for (String sock : socks) {
            String[] parts = sock.split(",");
            String color = parts[0];
            String foot = parts[1];
            String opposite = foot.equals("right") ? ",left" : ",right";
            String key = color + opposite;
            if (map.containsKey(key)) {
                listOfPairs.add(color);
                map.put(key, map.get(key) - 1);
                if (map.get(key) == 0) {
                    map.remove(key);
                }
            } else {
                map.put(sock, map.getOrDefault(sock, 0) + 1);
            }
        }
        System.out.println("list of pairs: " + listOfPairs);
        System.out.println("unmatched: " + map);
    }
}
