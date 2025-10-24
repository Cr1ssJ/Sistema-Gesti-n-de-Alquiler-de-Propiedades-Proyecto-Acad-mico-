package utp.ac.pa.sistema.utils;

import java.util.Scanner;

public class IOUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt){
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static void println(String msg){
        System.out.println(msg);
    }
}
