import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaData {
    // meta data node has ID n0
    // Yes, this is hacky. idc
    static final String META = "n0";
    Map<String, String> stringMap = new HashMap<>();
    Map<String, Integer> integerMap = new HashMap<>();
    public MetaData(int rng, int maxNodes, int maxEdges){
        stringMap.put("type", "Meta");
        integerMap.put("rng", rng);
        integerMap.put("edges", 0);
        integerMap.put("nodes", 0);
        integerMap.put("maxNodes", maxNodes);
        integerMap.put("maxEdges", maxEdges);
    }
    public void setRNG(int rng){
        integerMap.put("rng", rng);
    }
    public void setMaxNodes(int maxNodes){
        integerMap.put("maxNodes", maxNodes);
    }
    public void setMaxEdges(int maxEdges){
        integerMap.put("maxEdges", maxEdges);
    }
    public void getRNG(){
        integerMap.get("rng");
    }
    public void getMaxNodes(){
        integerMap.get("maxNodes");
    }
    public void getMaxEdges(){
        integerMap.get("maxEdges");
    }

    public void setMeta() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        File f = Path.of("generator.gps/TrivialTest.gst").toFile();
        File dest = Path.of("generator.gps/result.gst").toFile();
//        MetaEditor.MetaData meta = new MetaEditor.MetaData(-1, 5, 5);
        setMeta(f, dest);
    }

    public void setMeta(File graphFile, File dest) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(graphFile);

        NodeList edges = doc.getElementsByTagName("edge");
        // remove all existing data edges for the metaData node
        for (int i = 0; i < edges.getLength(); i++) {
            Node edge = edges.item(i);
            NamedNodeMap attrs = edge.getAttributes();
            if (attrs.getNamedItem("from").getNodeValue().equals(META)
                    && attrs.getNamedItem("to").getNodeValue().equals(META)) {
                edge.getParentNode().removeChild(edge);
            }
        }
        //have the new information inserted
        inserXML(doc);

        //write again
        DOMSource src = new DOMSource(doc);
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        StreamResult res = new StreamResult(dest);
        tr.transform(src, res);
    }
    private void inserXML(Document doc){
        Node graph = doc.getElementsByTagName("graph").item(0);
        Element edge = doc.createElement("edge"),
                attr = doc.createElement("attr"),
                str = doc.createElement("string");
        edge.setAttribute("from", META);
        edge.setAttribute("to", META);
        attr.setAttribute("name", "label");
        attr.appendChild(str);
        edge.appendChild(attr);
        List<String> texts = new ArrayList<>();
        for (Map.Entry<String, String> entry : stringMap.entrySet()){
            texts.add(entry.getKey()+":"+entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : integerMap.entrySet()) {
            texts.add("let:"+entry.getKey()+" = "+entry.getValue());
        }
        for (String s : texts){
            Node text = doc.createTextNode(s);
            Node newEdge = edge.cloneNode(true);
            newEdge
                    .getFirstChild() //attr
                    .getFirstChild() //str
                    .appendChild(text); // insert text
            graph.appendChild(newEdge);
        }
    }
}
