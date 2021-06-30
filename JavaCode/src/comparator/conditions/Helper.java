package comparator.conditions;

import datastructures.Edge;
import datastructures.Graph;
import datastructures.Node;

import java.util.HashMap;
import java.util.HashSet;

public class Helper {

    public static HashMap<String, HashSet<String>> getNeighbours(Graph g) {
        HashMap<String, HashSet<String>> neighbours = new HashMap<>();
        for (Node n: g.getNodes()) {
            neighbours.put(n.getId(), new HashSet<>());
        }
        for (Edge e: g.getEdges()) {
            String u = e.getN1().getId();
            String v = e.getN2().getId();

            neighbours.get(u).add(v);
            neighbours.get(v).add(u);
        }
        return neighbours;
    }
}
