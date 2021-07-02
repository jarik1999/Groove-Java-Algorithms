package comparator.conditions;

import datastructures.Graph;
import datastructures.Node;

import java.util.HashMap;
import java.util.HashSet;

public class HasCycle implements Condition {
    @Override
    public boolean run(Graph G) {
//        Condition connected = new Connected();
//        return connected.run(G) && (G.getEdges().size() >= G.getNodes().size());

        HashMap<String, HashSet<String>> neighbours = Helper.getNeighbours(G);
        HashSet<String> visited = new HashSet<>();
        for (Node node: G.getNodes()) {
            if (!visited.contains(node.getId())) {
                if (dfs(node.getId(), null, neighbours, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean dfs(String node, String parent, HashMap<String, HashSet<String>> neighbours, HashSet<String> visited) {
        if (visited.contains(node)) {
            return true;
        }
        visited.add(node);
        for (String neighbour: neighbours.get(node)) {
            if (neighbour.equals(parent)) {
                continue;
            }
            if (dfs(neighbour, node, neighbours, visited)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getConditionName() {
        return "HasCycle";
    }
}
