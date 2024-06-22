package misc;

import java.util.*;
import java.util.regex.Pattern;

public class DenyListedIP {


    public static void main(String[] args) {
        String[] denyListedIps = {"*111.*", "123.*", "34.*"};
        String[] requests = {"123.1.23.34", "121.1.23.34", "121.1.23.34", "34.1.23.34", "121.1.23.34", "12.1.23.34", "121.1.23.34"};

        List<Pattern> patterns = Arrays.stream(denyListedIps).
                map(ip -> Pattern.compile(ip.replace(".", "\\.").replace("*", ".*"))).
                toList();

        Map<String, List<Integer>> recentRequests = new HashMap<>();

        for (int i=0; i<requests.length; i++) {
            boolean blocked = false;

            for (Pattern p : patterns) {
                if (p.matcher(requests[i]).matches()) {
                    blocked = true;
                    break;
                }
            }

            if (blocked) {
                System.out.println(String.format("%s is blocked because of deny list", requests[i]));
            } else {
                recentRequests.putIfAbsent(requests[i], new ArrayList<>());
                List<Integer> reqHistory = recentRequests.get(requests[i]);
                Iterator<Integer> iterator = reqHistory.iterator();
                while(iterator.hasNext()) {
                    if (i - iterator.next() > 5) {
                        iterator.remove();
                    }
                }

                reqHistory.add(i);

                if (reqHistory.size() > 1) {
                    System.out.println(String.format("%s is blocked because of frequent access", requests[i]));
                }
            }
        }

    }
}
