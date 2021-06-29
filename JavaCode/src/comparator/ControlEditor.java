package comparator;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ControlEditor {
    public static Path PWD = Paths.get(System.getenv("PWD"));
    public static final Path GROOVE_DIR = PWD.getParent().resolve("Groove.gps");
    private static final Path MAIN_CONTROL = GROOVE_DIR.resolve("RunRule.gcp");
    private static final String bool_prog = """
            try {
            \t%s;
            \tSetResultTrue;
            } else {
            \tSetResultFalse;
            }""";
    public static List<String> ruleNames = List.of("IsComplete",
            "isConnectedCondition",
            "IsEmpty",
            "IsSimple",
            "IsTrivial",
            "NotEmpty",
            "HasCycle()");

    public static void setRule(String ruleName) throws IOException {
        try (FileWriter x = (new FileWriter(MAIN_CONTROL.toFile(), false))) {
            x.write(String.format(bool_prog, ruleName));
        } catch (IOException e) {
            System.err.println("Something went wrong while trying to write a control program:");
            e.printStackTrace();
            throw e;
        }
    }
}
