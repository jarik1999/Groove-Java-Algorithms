package datastructures;

public interface Generics<N extends Node, E extends Edge> {
    public N makeNode(Graph G, String id, ExtraData data);
    public E makeEdge(Graph G, Node n1, Node n2, String id, ExtraData data);
    public E makeStringEdge(Graph G, String n1, String n2, String id, ExtraData data);
};
