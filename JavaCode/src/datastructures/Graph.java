package datastructures;

import java.util.*;

public class Graph {
    private String name;
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final Map<String, Node> nodeMap = new HashMap<>();
    private ExtraData data = new ExtraData(new ArrayList<>());
    public Graph(String name){
        this.name=name;
    }
    //ADD
    public void addEdge(Edge e){
        edges.add(e);
    }
    public void addNode(Node n){
        nodes.add(n);
        nodeMap.put(n.getId(), n);
    }
    public void addData(ExtraData data) {
        this.data.merge(data);
    }
    //REMOVE
    public void deleteEdge(Edge e){
        edges.remove(e);
    }
    public void deleteNode(Node n){
        nodes.remove(n);
        nodeMap.remove(n);
    }

    //GET
    public Node getNode(String id){
        return nodeMap.get(id);
    }
    public String getName() {
        return name;
    }
    public List<Edge> getEdges(){
        return edges;
    }
    public List<Node> getNodes(){
        return nodes;
    }
    public ExtraData.DataValue getData(String key) {
        return data.get(key);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "\n\tname='" + name + '\'' +
                "\n\tnodes={"+
                (!nodes.isEmpty()?
                    "\n\t\t"+String.join("\n\t\t", nodes.stream()
                            .map(Node::toString)
                            .toArray(String[]::new)) +
                "\n\t":"")+
                "}\n\tedges={"+
                (!edges.isEmpty()?
                    "\n\t\t"+String.join("\n\t\t", edges.stream()
                        .map(Edge::toString)
                        .toArray(String[]::new)) +"\n\t"
                    :"")+
                "}\n}";


    }


}
