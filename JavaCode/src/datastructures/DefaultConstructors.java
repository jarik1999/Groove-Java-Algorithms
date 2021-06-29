package datastructures;

public class DefaultConstructors implements Generics{
    /**
     * use for graphs without any extra values attached.
     */
    public static DefaultConstructors INST = new DefaultConstructors();
    @Override
    public Node makeNode(Graph G, String id, ExtraData data) {
        return new Node(G, id, data);
    }

    @Override
    public Edge makeEdge(Graph G, Node n1, Node n2, String id, ExtraData data) {
        return new Edge(G, n1, n2, id, data);
    }

    @Override
    public Edge makeStringEdge(Graph G, String n1, String n2, String id, ExtraData data) {
        return new Edge(G, G.getNode(n1), G.getNode(n2), id, data);
    }
}
