package comparator.conditions;

import datastructures.Graph;

import java.util.HashMap;
import java.util.HashSet;

public class Connected implements Condition {
    @Override
    public boolean run(Graph G) {
        if (G.getNodes().size() == 0) {
            return true;
        }
        HashSet<String> visited = new HashSet<>();
        HashMap<String, HashSet<String>> neighbours = Helper.getNeighbours(G);
        dfs(G.getNodes().get(0).getId(), neighbours, visited);
        return visited.size() == G.getNodes().size();
    }

    private void dfs(String node, HashMap<String, HashSet<String>> neighbours, HashSet<String> visited) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        for (String neighbour: neighbours.get(node)) {
            dfs(neighbour, neighbours, visited);
        }
    }

    @Override
    public String getConditionName() {
        return "IsConnected";
    }
}
