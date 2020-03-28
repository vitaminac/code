package hackercup.y2019;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.Scanner;

public class Q25MrX {
    public static void main(String[] args) throws IOException, ScriptException {
        try (
                Scanner sc = new Scanner(new BufferedReader(new FileReader("mr_x.txt")));
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mr_x.out")))
        ) {
            int T = sc.nextInt();
            sc.nextLine();
            for (int i = 1; i <= T; i++) {
                String expr = sc.nextLine();
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                if (Integer.parseInt(engine.eval(expr.replace('X', '0').replace('x', '1')).toString())
                        != Integer.parseInt(engine.eval(expr.replace('X', '1').replace('x', '0')).toString())) {
                    pw.println("Case #" + i + ": 1");
                } else {
                    pw.println("Case #" + i + ": 0");
                }
            }
            pw.flush();
        }
    }
}
