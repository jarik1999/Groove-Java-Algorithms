package comparator;

import comparator.conditions.Condition;
import datastructures.ExtraData;
import datastructures.Graph;
import datastructures.ResultConstructors;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Compare<C extends Condition>{
    private static final File TMP = new File("tmp/resultGraph");
    private C condition;
    private Runner r;

    public Compare(C condition){
        this.condition = condition;
        //prepare command
        r = new Runner();
        r.addArg("-D", "controlProgram=RunRule");
        r.addArg("-r", "1"); //one result is enough
        r.addArg("-f", TMP.getAbsolutePath()); //location to store file in.
    }

    public boolean compare(File file) throws InterruptedException, SAXException, ParserConfigurationException, IOException {
        String prefix = "";
        if (file.getParentFile().getName().equals("generated")){
            prefix = "generated.";
        }
        try {
            // graph to test with
            r.addArgOnce("-D", String.format("startGraph=%s", prefix + file.getName()));
            //run the test
            r.run();
            //collect the output
            Graph G = GraphReader.readGraph(TMP.toPath(), ResultConstructors.INST);
            ExtraData.DataValue dv = G.getData("applicable");

            boolean r2 = condition.run(G);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void setRule() throws IOException {
        ControlEditor.setRule(this.condition.getConditionName());
    }

    public void compare(Path graphfile){

    }
}
