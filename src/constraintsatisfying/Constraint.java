package constraintsatisfying;

import java.util.List;
import java.util.Map;

// v - variable, d - domain
public abstract class Constraint<V, D> {
    protected List<V> variables;

    public Constraint(List<V> variables) {
        this.variables = variables;
    }

    public abstract boolean satisfied(Map<V, D> assignment);
}
