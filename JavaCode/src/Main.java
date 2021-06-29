import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        int[] nodeNR = IntStream.range(1, 3).map(n -> n * 5).toArray();
        int[] edgeNR = IntStream.range(1, 3).map(n -> n * 5).toArray();
        runBatches(10, nodeNR, edgeNR);

    }

    public static void runBatches(int batchSize, int[] nodeNR, int[] edgeNR) throws IOException, InterruptedException, TransformerException, ParserConfigurationException, SAXException {
        Generator G = new Generator(batchSize, null);
        MetaData data = new MetaData(-1, -1, -1);
        Random rand = new Random();
        for (int j : nodeNR) {
            data.setMaxNodes(j);
            for (int k : edgeNR) {
                data.setRNG(rand.nextInt(101));
                data.setMaxEdges(k);
                data.setMeta();
                G.setNameDir("tmp/N" + j + "E" + k + "graph#");
                G.execute();
            }
        }
    }
}
