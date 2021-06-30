package comparator.conditions;

import datastructures.Edge;
import datastructures.Graph;
import datastructures.Node;

import java.util.HashMap;
import java.util.HashSet;

public class Simple implements Condition {
    @Override
    public boolean run(Graph G) {
        // For each node store it's neighbour.
        HashMap<String, HashSet<String>> neighbours = new HashMap<>();
        for (Edge e: G.getEdges()) {
            String u = e.getN1().getId();
            String v = e.getN2().getId();

            if (!neighbours.containsKey(u)) {
                neighbours.put(u, new HashSet<>());
            }
            if (!neighbours.containsKey(v)) {
                neighbours.put(v, new HashSet<>());
            }

            if (u.equals(v)) {
                // Simple graphs cannot have self-loops
                return false;
            }
            if (neighbours.get(u).contains(v) || neighbours.get(v).contains(u)) {
                // We already found an edge connecting u and v.
                return false;
            }

            neighbours.get(u).add(v);
            neighbours.get(v).add(u);
        }
        return true;
    }

    @Override
    public String getConditionName() {
        return "IsSimple";
    }
}
