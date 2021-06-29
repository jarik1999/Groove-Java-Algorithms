package comparator;

import datastructures.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class GraphReader {
    public static final String sep = FileSystems.getDefault().getSeparator();
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        doDirectory(Path.of(String.join(sep, "..","..","tmp")));
        System.out.println(new Graph("a"));
    }
    public static void doDirectory(Path directory) throws ParserConfigurationException, SAXException, IOException {
        assert directory.toFile().isDirectory();
        File f = directory.toFile();
        File[] ls = f.listFiles();
        Arrays.sort(ls);
        for (File sub : Objects.requireNonNull(ls)){
            if (!sub.getName().endsWith(".gps")) continue;
            Graph G = readGraph(sub.getName(), directory, DefaultConstructors.INST);
            System.out.println(G);
        }
    }

    public static <N extends datastructures.Node, E extends Edge>
    Graph readGraph(String filename, Path dir, Generics<N, E> INST)
            throws IOException, SAXException, ParserConfigurationException {
        readGraph(dir.resolve(filename), INST);
    }
    public static <N extends datastructures.Node, E extends Edge>
    Graph readGraph(Path file, Generics<N, E> INST)
            throws IOException, SAXException, ParserConfigurationException {
        //concat directoy & file name to get file
        File f = file.toFile();
        if (!f.isFile()) throw new IllegalArgumentException("the resulting path '" + file + "' is not a file");

        try {
            // Do XML stuffs to read file
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            //collect relevant parts
            NodeList nodes = doc.getElementsByTagName("node");
            NodeList edges = doc.getElementsByTagName("edge");
            // Store for information retrieved from XML
            Map<String, List<String>> nodeAttrs = new HashMap<>();
            Map<String, List<String>> edgeEnds = new HashMap<>();

            //get all nodes in the graph
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                //only thing stored about the node is the ID, text on the node is stored as an edge.
                String id = n.getAttributes().getNamedItem("id").getNodeValue();
                //store it for later, this could be a node or edge in the graph.
                nodeAttrs.put(id, new ArrayList<>());
                edgeEnds.put(id, new ArrayList<>());
            }
            //now the edges :)
            for (int i = 0; i < edges.getLength(); i++) {
                Node n = edges.item(i);
                // the nodes that the edge connects
                String from = n.getAttributes().getNamedItem("from").getNodeValue();
                String to = n.getAttributes().getNamedItem("to").getNodeValue();
                // dig to find the text on the edge
                Node attr = getChild(n);
                assert attr.getNodeName().equals("attr");
                assert attr.getAttributes().getNamedItem("name").getNodeValue().equals("label");
                Node textNode = getChild(attr);
                assert textNode.getNodeName().equals("string");
                //found it
                String text = textNode.getTextContent();
                assert !text.isEmpty();
                if (from.equals(to)) {
                    // this is a label on a node.
                    nodeAttrs.get(from).add(text);
                } else {
                    // this is an edge that points from an 'type:Edge' node to a 'type:Node' node
                    assert text.equals("edge");
                    edgeEnds.get(from).add(to);
                }
            }
            // now use the XML data collected to construct the graph.
            Graph G = new Graph(f.getName());
            for (String id : nodeAttrs.keySet()) {
                List<String> labels = nodeAttrs.get(id);
                List<String> ends = edgeEnds.get(id);
                if (ends.isEmpty()) {
                    // this is a node of G
                    INST.makeNode(G, id, new ExtraData(labels));
                }
            }
            for (String id : nodeAttrs.keySet()) {
                List<String> labels = nodeAttrs.get(id);
                List<String> ends = edgeEnds.get(id);
                if (!ends.isEmpty()) {
                    assert ends.size() == 2;
                    // this is an edge of G
                    INST.makeStringEdge(G, ends.get(0), ends.get(1), id, new ExtraData(labels));
                }
            }
            return G;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static Node getChild(Node n) throws SAXException {
        Node child = n.getFirstChild();
        while (child != null && child.getNodeType() == Node.TEXT_NODE) {
            child = child.getNextSibling();
        }
        if (child == null) {
            throw new SAXException("Node has no child");
        }
        return child;


    }
}