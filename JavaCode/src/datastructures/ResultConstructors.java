package datastructures;

public class ResultConstructors implements Generics{
    public static ResultConstructors INST = new ResultConstructors();
    @Override
    public Node makeNode(Graph G, String id, ExtraData data) {
        ExtraData.DataValue type = data.get("type");
        if (type.type == ExtraData.DataValueEnum.STRING && type.stringValue.equals("Result")) {
            G.addData(data);
            return null;
        } else {
            return new Node(G, id, data);
        }
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
