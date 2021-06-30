package comparator.conditions;

import datastructures.Graph;

import java.util.HashMap;
import java.util.HashSet;

public class Complete implements Condition {
    @Override
    public boolean run(Graph G) {
        HashMap<String, HashSet<String>> neighbours = Helper.getNeighbours(G);
        for (String u: neighbours.keySet()) {
            if (neighbours.get(u).contains(u)) {
                // Complete graphs should not have self-loops
                return false;
            }
            if (neighbours.get(u).size() != G.getNodes().size() - 1) {
                // Each node should have neighbours to n - 1 nodes, where n is total node
                return false;
            }
        }
        return true;
    }

    @Override
    public String getConditionName() {
        return "IsComplete";
    }
}
