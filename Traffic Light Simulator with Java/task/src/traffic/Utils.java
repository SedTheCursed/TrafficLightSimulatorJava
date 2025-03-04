package traffic;

import java.io.IOException;

public class Utils {

    /**
     * Clear the console
     */
    public static void cleanConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
    }


    /**
     * Output a string in the console and terminate the line.
     *
     * @param msg String to be printed
     */
    public static void println(String msg) {
        System.out.println(msg);
    }

    /**
     * Output a string in the console
     *
     * @param msg String to be printed
     */
    public static void print(String msg) {
        System.out.print(msg);
    }
}
