package comparator.algorithms;

import comparator.conditions.Helper;
import datastructures.Graph;
import datastructures.Node;

import java.util.HashMap;
import java.util.HashSet;

public class SCC {

    public int run(Graph g) {
        HashMap<String, HashSet<String>> neighbours = Helper.getNeighbours(g);
        HashSet<String> visited = new HashSet<>();
        int components = 0;
        for (Node n: g.getNodes()) {
            if (visited.contains(n.getId())) continue;
            Helper.dfs(n.getId(), neighbours, visited);
            components += 1;
        }
        return components;
    }

}
