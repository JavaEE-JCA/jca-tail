package ir.moke.fs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainTest {
    public static void main(String[] args) throws Exception {
        testTail();
    }

    public static void testTail() throws Exception {
        File file = new File("/var/log/syslog");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        bufferedReader.skip(file.length());
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                System.out.println(line);
            }
        }
    }
}
