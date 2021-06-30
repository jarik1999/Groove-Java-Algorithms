package comparator.conditions;

import datastructures.Graph;

public class Tree implements Condition{
    @Override
    public boolean run(Graph G) {
        Condition connected = new Connected();
        Condition cycle = new HasCycle();
        return connected.run(G) && !cycle.run(G);
    }

    @Override
    public String getConditionName() {
        return "IsTree";
    }
}
