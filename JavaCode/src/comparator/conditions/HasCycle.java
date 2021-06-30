package comparator.conditions;

import datastructures.Graph;
import datastructures.Node;

import java.util.HashMap;
import java.util.HashSet;

public class HasCycle implements Condition {
    @Override
    public boolean run(Graph G) {
        Condition connected = new Connected();
        return connected.run(G) && (G.getEdges().size() >= G.getNodes().size());
    }

    @Override
    public String getConditionName() {
        return "HasCycle";
    }
}
