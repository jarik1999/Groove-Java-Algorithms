package comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {
    public static boolean SILENT=false;
    /**
     * Run a Groove command
     */
    private static final String sep = FileSystems.getDefault().getSeparator();
    private Map<String, List<String>> args = new HashMap<>();
    private Map<String, List<String>> argsOnce = new HashMap<>();
    private final String JAVA_LOC;
    private final String filename = String.join(sep, "..","Groove.gps");
    private final String[] jar = new String[]{"-jar", String.join(sep, "..", "..", "..", "bin", "Generator.jar")};

    public Runner() {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            JAVA_LOC = "/Library/Java/JavaVirtualMachines/jdk1.8.0_172.jdk/Contents/Home/jre/bin/java";
        } else {//windows
            JAVA_LOC = "IDK";
        }

    }

    public void addArg(String key, String value) {
        List<String> vals = args.getOrDefault(key, new ArrayList<>());
        vals.add(value);
        args.put(key, vals);
    }
    public void addArgOnce(String key, String value) {
        List<String> vals = argsOnce.getOrDefault(key, new ArrayList<>());
        vals.add(value);
        argsOnce.put(key, vals);
    }

    public void run() throws IOException, InterruptedException {
        String[] command = unite();
        Runtime rt = Runtime.getRuntime();
        //run the command
        final Process p = rt.exec(command);
        //collect output
        BufferedReader brE = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader brO = new BufferedReader(new InputStreamReader(p.getInputStream()));
        p.waitFor();
        //if silent, dont do output
        if (SILENT) return;
        while (true){
            String line;
            if (brE.ready()) {
                line = brE.readLine();
            } else if (brO.ready()){
                line = brO.readLine();
//                if (line.startsWith("State saved:")) continue;
            } else break;
//            if (line == null) break;
            System.out.println(line);
        }
        System.out.println("exit code:"+ p.exitValue());
    }

    private String[] unite() {
        List<String> arguments = new ArrayList<>();
        for (String key : args.keySet()){
            List<String> vals = args.get(key);
            for (String val : vals) {
                arguments.add(key);
                arguments.add(val);
            }
        }
        for (String key : argsOnce.keySet()){
            List<String> vals = argsOnce.get(key);
            for (String val : vals) {
                arguments.add(key);
                arguments.add(val);
            }
        }
        argsOnce.clear();
        String[] argArray = arguments.toArray(new String[0]);
        //now construct the entire command
        String[] command = new String[4+arguments.size()];
        command[0]=JAVA_LOC;
        System.arraycopy(jar, 0, command, 1, jar.length);
        System.arraycopy(argArray, 0, command, 1+jar.length, command.length);
        command[command.length-1]=filename;
        return command;
    }
}