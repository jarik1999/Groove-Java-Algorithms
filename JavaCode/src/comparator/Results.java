package comparator;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static comparator.GraphReader.sep;

public class Results {
    final File[] files;
    final long[][] results;
    public Results(File[] files, long[][] results){
        assert files.length==results.length;
        this.files=files;
        this.results=results;
    }
    public void printReport(){
//        StringBuilder report = new StringBuilder();
        String[][] report = new String[files.length][results[0].length+1];
        for (int i=0;i<files.length;i++) {
            report[i] = new String[]{files[i].getName(), ""+results[i][0], ""+results[i][1], ""+results[i][2]};
        }
        int[] maxWdith = new int[report[0].length];
        for (String[] strings : report) {
            for (int j = 0; j < report[0].length; j++) {
                maxWdith[j] = Math.max(maxWdith[j], strings[j].length());
            }
        }
        String[] headers = new String[]{"File", "Java (ns)", "Groove (ms)", "Groove total (ms)"};
        for (int j = 0; j < report[0].length; j++) {
            maxWdith[j] =Math.max(maxWdith[j], headers[j].length());
        }
        for (int j = 0; j < report[0].length; j++) {
            maxWdith[j] +=5;
        }
        StringBuilder formatter = new StringBuilder();
        for (int j = 0; j < report[0].length; j++) {
            formatter.append("%").append(maxWdith[0]).append("s");
        }
        formatter.append('\n');
        String format = formatter.toString();
        StringBuilder total = new StringBuilder();
        total.append(String.format(format, (Object[]) headers));
        for (int i=0;i<report.length;i++){
            total.append(String.format(format, (Object[]) report[i]));
        }
        System.out.println(total.toString());
    }
    public void toFile(String filename){
        if (!filename.contains(".")){
            filename += ".csv";
        }
        File file = new File(String.join(sep, "results", filename));
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (int i=0;i<results.length;i++){
                pw.println(String.join(",",
                        files[i].getName(),
                        ""+results[i][0],
                        ""+1000000*results[i][1],
                        ""+1000000*results[i][2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
