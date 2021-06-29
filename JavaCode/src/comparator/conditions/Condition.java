package comparator.conditions;

import datastructures.Graph;

public interface Condition {
    public boolean run(Graph G);
    public String getConditionName();
}
