package comparator.algorithms;

import datastructures.Edge;
import datastructures.Graph;
import datastructures.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MST {

    public int run(Graph g) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(g.getEdges());
        HashMap<String, Integer> id = new HashMap<>();
        int index = 0;
        for (Node n: g.getNodes()) {
            id.put(n.getId(), index);
            index++;
        }
        DSU dsu = new DSU(g.getNodes().size());
        int result = 0;

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int u = id.get(e.getN1().getId());
            int v = id.get(e.getN1().getId());

            if (dsu.sameSet(u, v)) {
                continue;
            }
            result += e.getWeight();
            dsu.union(u, v);
        }

        return result;
    }

    private class DSU {
        int[] p;

        public DSU(int n) {
            p = new int[n];
            clear();
        }

        private void clear() {
            for (int i = 0; i < p.length; i++) p[i] = i;
        }

        public int get(int x) {
            int x2 = x;
            while (x2 != p[x2]) x2 = p[x2];
            while (x != p[x]) {
                int t = p[x];
                p[x] = x2;
                x = t;
            }
            return x2;
        }

        public void union(int a, int b) {
            a = get(a);
            b = get(b);
            p[a] = b;
        }

        public boolean sameSet(int a, int b) {
            return get(a) == get(b);
        }

        private ArrayList<Integer>[] getSets() {
            HashMap<Integer, ArrayList<Integer>> sets = new HashMap<>();
            for (int i = 0; i < p.length; i++) {
                int parent = get(i);
                if (!sets.containsKey(parent)) sets.put(parent, new ArrayList<>());
                sets.get(parent).add(i);
            }
            int i = 0;
            ArrayList<Integer>[] res = new ArrayList[sets.size()];
            for (ArrayList<Integer> set: sets.values()) res[i++] = set;
            return res;
        }
    }
}

