package constraintsatisfying;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// important concepts to understand: variable, domain value, constraint, assignment
public class CSP<V, D> {
    // a variable has a number of domains
    private Map<V, List<D>> domains;
    // a variable has a number of constraints
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(Map<V, List<D>> domains) {
        this.domains = domains;
    }

    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!domains.keySet().contains(variable)) {
                throw new IllegalArgumentException("variable in constraint not in CSP");
            }

            constraints.putIfAbsent(variable, new ArrayList<>());
            constraints.get(variable).add(constraint);
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    public Map<V, D> backtracking(Map<V, D> assignment) {
        if (assignment.size() == domains.size()) {
            return assignment;
        }

        // get the first unassigned variable
        V unassigned = domains.keySet().stream().filter(v -> !assignment.containsKey(v)).findFirst().get();

        // assign every possible domain value to the unassigned variable

        for (D value : domains.get(unassigned)) {
            // make a shallow copy as local assignment
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);

            if (consistent(unassigned, localAssignment)) {
                Map<V, D> result = backtracking(localAssignment);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }
}
