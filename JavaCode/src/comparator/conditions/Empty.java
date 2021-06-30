package comparator.conditions;

import datastructures.Graph;

public class Empty implements Condition {
    @Override
    public boolean run(Graph G) {
        return G.getNodes().size() == 0 && G.getEdges().size() == 0;
    }

    @Override
    public String getConditionName() {
        return "IsEmpty";
    }
}
