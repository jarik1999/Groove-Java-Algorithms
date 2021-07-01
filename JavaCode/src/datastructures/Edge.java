package datastructures;

import java.util.HashMap;
import java.util.Map;

public class Edge implements Comparable<Edge> {
    private final int weight;
    private final Node n1, n2;
    private final String id;
    private final Graph G;

    public Edge(Graph G, Node n1, Node n2, String id, ExtraData data) {
        ExtraData.DataValue type = data.get("type");
        assert type.type == ExtraData.DataValueEnum.STRING && type.stringValue.equals("Edge");
        this.G = G;
        this.id = id;
        this.n1 = n1;
        this.n2 = n2;
        n1.addNeighbour(n2, this);
        n2.addNeighbour(n1, this);
        G.addEdge(this);
        ExtraData.DataValue w = data.get("weight");
        assert w.type == ExtraData.DataValueEnum.INT;
        this.weight = w.intValue;
    }
    //REMOVE
    public void delete(){
        n1.deleteNeighbour(n2);
        n2.deleteNeighbour(n1);
        G.deleteEdge(this);
    }

    //GET
    public Node getOther(Node n){
        if (n==n1) return n2;
        if (n==n2) return n1;
        throw new IllegalArgumentException("given node is not an end of this edge");
    }

    public Node getN1() {
        return n1;
    }
    public Node getN2(){
        return n2;
    }
    public int getWeight() {
        return weight;
    }
    public Graph getG() {
        return G;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id='" + id + "' [" +
                n1.getId() + " - " + n2.getId() +
                "] weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }

}
