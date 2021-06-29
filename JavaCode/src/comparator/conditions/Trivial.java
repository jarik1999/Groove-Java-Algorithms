package comparator.conditions;

import datastructures.Graph;

public class Trivial implements Condition {
    @Override
    public boolean run(Graph G) {
        return (G.getEdges().size()==0) && (G.getNodes().size()==1);
    }

    @Override
    public String getConditionName() {
        return "IsTrivial";
    }
}
