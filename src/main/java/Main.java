import java.io.*;
import java.sql.DriverManager;

import static java.lang.System.out;
import static java.sql.DriverManager.getLogStream;

public class Main {

    //fun only!!!!
    public static void main(String[] args) throws IOException {
        //trash();

        DriverManager.drivers().forEach(driver -> {
            out.println("Driver: " + driver.getClass().getName());
        });
    }

    private static void trash() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


        OutputStream OutputStream = null;
        DriverManager.setLogStream(new PrintStream(String.valueOf(Integer.parseInt(reader.readLine()))));
        PrintStream logStream = getLogStream();
        //writer.write(String.valueOf(out.println("BEFORE ID: " + new PrintStream(logStream).toString())));
        out.println("ID: " + logStream);
        ;

          /*
        Пример ввода и вывода числа n, где -10^9 < n < 10^9:
        int n = Integer.parseInt(reader.readLine());
        writer.write(String.valueOf(n));
        */

        reader.close();
        writer.close();
    }
}
