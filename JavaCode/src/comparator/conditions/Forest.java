package comparator.conditions;

import datastructures.Graph;

public class Forest implements Condition {
    @Override
    public boolean run(Graph G) {
        Condition cycle = new HasCycle();
        return !cycle.run(G);
    }

    @Override
    public String getConditionName() {
        return "IsForest";
    }
}
