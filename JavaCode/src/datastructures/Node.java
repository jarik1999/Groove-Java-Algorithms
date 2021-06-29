package datastructures;

import java.util.*;

public class Node {
    private final String id;
    private final Graph G;
    private final Map<Node, Edge> neighbours = new HashMap<>();

    public Node(Graph G, String id, ExtraData data) {
        ExtraData.DataValue type = data.get("type");
        assert type.type == ExtraData.DataValueEnum.STRING && type.stringValue.equals("Node");
        this.G = G;
        this.id=id;
        G.addNode(this);
    }
    // remove all incident edges
    public void clear(){
        for (Edge e: new ArrayList<>(getEdges())){
            e.delete();
        }
    }
    //delete this node
    public void delete(){
        clear();
        G.deleteNode(this);
    }
    //used by Edge, do not use yourself
    public void addNeighbour(Node other, Edge e){
        if (other == this) throw new IllegalArgumentException("cannot be neighbours to self");
        if (neighbours.containsKey(other)) throw new IllegalArgumentException("cannot be a neighbour more than once.");
        neighbours.put(other, e);
    }
    //used by Edge, do not use yourself
    public void deleteNeighbour(Node n) {
        neighbours.remove(n);
    }

    //GET
    public Map<Node, Edge> getNeighbourMap(){
        return neighbours;
    }
    public Set<Node> getNeighbours() {
        return neighbours.keySet();
    }
    public Collection<Edge> getEdges(){
        return neighbours.values();
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                '}';
    }
}
