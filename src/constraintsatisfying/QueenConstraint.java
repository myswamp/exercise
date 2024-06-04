package constraintsatisfying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueenConstraint extends Constraint<Integer, Integer> {

    public QueenConstraint(List<Integer> rows) {
        super(rows);
    }

    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        for (Map.Entry<Integer, Integer> entry : assignment.entrySet()) {
            int q1r = entry.getKey();
            int q1c = entry.getValue();
            for (int q2r = q1r+1; q2r <= variables.size(); q2r++) {
                if (assignment.containsKey(q2r)) {
                    int q2c = assignment.get(q2r);
                    if(q1c == q2c) {
                        return false;
                    }

                    // diagonal
                    if (Math.abs(q1r - q2r) == Math.abs(q1c - q2c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        List<Integer> rows = List.of(1,2,3,4,5,6,7,8);

        Map<Integer, List<Integer>> domains = new HashMap<>();

        for (Integer row : rows) {
            domains.put(row, List.of(1,2,3,4,5,6,7,8));
        }

        CSP<Integer, Integer> csp = new CSP<>(domains);

        csp.addConstraint(new QueenConstraint(rows));

        Map<Integer, Integer> solution = csp.backtracking(new HashMap<>());

        if (solution == null) {
            System.out.println("no solution found");
        } else {
            System.out.println(solution);
        }
    }

}
