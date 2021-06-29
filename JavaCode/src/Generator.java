import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Generator {
    String[] begin;
    String[] common;
    Runtime rt;
//    public void setMeta(MetaEditor.MetaData data){
//        File f = Path.of("generator.gps/TrivialTest.gst").toFile();
//        File dest = Path.of("generator.gps/result.gst").toFile();
//        MetaEditor.MetaData meta = new MetaEditor.MetaData(-1, 5, 5);
//        setMeta(f, dest, meta);
//    }

    /**
     *
     * @param results number of files stored
     * @param nameDir  "#" is required. e.g. "tmp/graph#"
     */
    public Generator(int results, String nameDir) {
        common = new String[]{"generator.gps", // Groove project
                "-f", nameDir, //name of the files to save to
                "-s", "dfs",  // strategy. (rete)random yields 1 state only
                "-r", "" + results, // amount of results
                "-a", "ruleapp:Final"//"inv:!Final" // what states to save, here: where rule 'Final' is not applicable
        };
        // the stuff needed to run the generator jar
        if (System.getProperty("os.name").equals("Mac OS X")) {
            begin = new String[]{"sh", "gen"};
        } else {//windows
            begin = new String[]{"cmd", "/c", "gen.bat"};
        }
        // Execute it and print output to console.
        rt = Runtime.getRuntime();
    }
    public void setResults(int results){
        common[6]=""+results;
    }
    public void setNameDir(String name){
        common[2]=name;
    }

    /**
     * Run the generator to generate a number of files
     * @throws InterruptedException
     * @throws IOException
     */
    public void execute() throws InterruptedException, IOException {
        String[] command = new String[begin.length + common.length];
        System.arraycopy(begin, 0, command, 0, begin.length);
        System.arraycopy(common, 0, command, begin.length, common.length);
        final Process p = rt.exec(command);
        BufferedReader brE = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader brO = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (true){
            String line;
            if (brE.ready()) {
                line = brE.readLine();
            } else if (brO.ready()){
                line = brO.readLine();
                if (line.startsWith("State saved:")) continue;
            } else continue;
            if (line == null || line.equals("DONE")) break;
            System.out.println(line);
        }
        p.waitFor();
        System.out.println("exit code:"+ p.exitValue());
    }
}
