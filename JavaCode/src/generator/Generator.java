package generator;

import comparator.Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Generator {
    String[] begin;
    String[] common;
    Runtime rt;
    Runner runner;

    /**
     *
     * @param results number of files stored
     * @param nameDir  "#" is required. e.g. "tmp/graph#"
     */
    public Generator(int results){//}, String nameDir) {
        this.runner = new Runner("generator");
//        runner.addArg("-f", nameDir);//name of the files to save to
        runner.addArg("-s", "dfs");// strategy. (rete)random yields 1 state only
        runner.addArg("-r", ""+results); // amount of results
        runner.addArg("-a", "ruleapp:Final"); //"inv:!Final" // what states to save, here: where rule 'Final' is not applicable
    }
    public void setNameDir(String name){
        runner.addArgOnce("-f", name);//name of the files to save to
    }

    /**
     * Run the generator to generate a number of files
     * @throws InterruptedException
     * @throws IOException
     */
    public void execute() throws InterruptedException, IOException {
        long[] times = runner.run();
        System.out.println("Groove time="+times[0]+" ms, total time="+times[1]+" ms.");
    }
}
