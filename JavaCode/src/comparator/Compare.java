package comparator;

import comparator.conditions.*;
import datastructures.ExtraData;
import datastructures.Graph;
import datastructures.ResultConstructors;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static comparator.GraphReader.sep;
import static comparator.Runner.SILENT;

public class Compare<C extends Condition>{
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        SILENT=true;
        Condition[] CS = new Condition[]{
                /*new Trivial(),*/
//                new Forest(),
                /* new Empty(),*/
//                new HasCycle(),
//                new Tree(),
//                new Simple(),
//                new Connected(),
                new Complete()
        };

//        CS = new Condition[]{new Forest()};//, new Empty(), new HasCycle(), new Tree(), new Simple(), new Connected()};
        File dir = new File(String.join(sep, "..", "Groove.gps", "large"));//"large"));
        File[] list = dir.listFiles();
        assert list != null;
        list = Arrays.stream(list).filter(f-> f.getName().endsWith(".gst")).toArray(File[]::new);
        System.out.println(Arrays.toString(list));
        for (Condition C : CS) {
            long mili = System.currentTimeMillis();
            ControlEditor.setRule(C);
            Compare c = new Compare(C);
            long[][] results = c.compare(list);
            Results r = new Results(list, results);
            r.printReport();
            r.toFile(C.getConditionName());
            System.out.println("Time of "+C.getConditionName()+ " is " +(System.currentTimeMillis()-mili)+" ms");
        }
        System.out.println("Total time "+(System.currentTimeMillis()-start)/1000 + " s");
    }

    private static final File TMP = new File("tmp/resultGraph.gst");
    private C condition;
    private Runner r;

    public Compare(C condition){
        this.condition = condition;
        //prepare command
        r = new Runner("Groove");
        //need to have controlProgram=RunRule & SCC, but idk how
//        r.addArg("-D", "controlProgram=RunRule");
        r.addArg("-r", "1"); //one result is enough
        r.addArg("-f", TMP.getAbsolutePath()); //location to store file in.
        r.addArg( "-a", "ruleapp:Done"); // define what states are final (and thus should be saved)
    }
    public long[][] compare(File[] files){
        long[][] results = new long[files.length][];
        for (int i=0;i<files.length;i++){
            results[i] = compare(files[i]);
        }
        return results;

    }

    public long[] compare(File file) {
        String prefix = "";
        File tmp = file.getParentFile();
        while (!tmp.getName().equals("Groove.gps")){
            prefix = tmp.getName()+'.'+prefix;
            tmp = tmp.getParentFile();
        }
        try {
            // graph to test with
            String filename = file.getName();
            String qualName = filename.substring(0, filename.length()-4);
            r.addArgOnce("-D", String.format("startGraph=%s", prefix + qualName));
            //run the test
            long[] runTimes = r.run();
            //collect the output
            Graph G = GraphReader.readGraph(TMP.toPath(), ResultConstructors.INST);
            ExtraData.DataValue dv = G.getData("success");
            assert dv!=null && dv.type== ExtraData.DataValueEnum.BOOL;
            boolean r1 = dv.boolValue;
            long startTime = System.nanoTime();
            boolean r2 = condition.run(G);
            long javaTime = System.nanoTime()-startTime;

            System.out.println("Java: "+javaTime +" ns, Groove: "+ runTimes[1] +" ms, Groove total: "+runTimes[0]+" ms");
            if (r1!=r2){
                System.out.println("Condition "+condition.getConditionName()+" differs on graph "+file.getName());
                System.out.println("Groove says "+r1+" but Java says "+r2);
            }
            return new long[]{javaTime, runTimes[1], runTimes[0]};
        } catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Condition "+condition.getConditionName()+" crashed on graph "+file.getName());
        return new long[]{-1,-1,-1};
    }
    public void setRule() throws IOException {
        ControlEditor.setRule(this.condition.getConditionName());
    }

    public void compare(Path graphfile){

    }
}
