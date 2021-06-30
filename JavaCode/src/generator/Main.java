package generator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        int minN = 50, maxN = 60, stepN = 1;
        double[] edgeMult = new double[]{2, 5, 10, 40};
        for (int k=0;k<edgeMult.length;k++) {
            System.out.println("hit"+k);
            int[][] nrs = new int[(maxN - minN) / stepN][2];
            for (int i = minN, j = 0; i < maxN; i += stepN, j++) {
                nrs[j][0] = i;
                nrs[j][1] = (int) (i * edgeMult[k]);
            }
//        int[] nodeNR = IntStream.range(1, 3).map(n -> n * 5).toArray();
//        int[] edgeNR = IntStream.range(1, 3).map(n -> n * 5).toArray();
            runBatches(10, nrs);
        }
    }

    public static void runBatches(int batchSize, int[][] nrs) throws IOException, InterruptedException, TransformerException, ParserConfigurationException, SAXException {
        Generator G = new Generator(batchSize);
        MetaData data = new MetaData(-1, -1, -1);
        Random rand = new Random();
        for (int[] nr : nrs) {
            data.setMaxNodes(nr[0]);
            data.setMaxEdges(nr[1]);
            data.setRNG(rand.nextInt(101));
            data.setMeta();
            G.setNameDir("../Groove.gps/large/N" + nr[0] + "E" + nr[1] + "graph#");
            G.execute();
        }
    }
}
