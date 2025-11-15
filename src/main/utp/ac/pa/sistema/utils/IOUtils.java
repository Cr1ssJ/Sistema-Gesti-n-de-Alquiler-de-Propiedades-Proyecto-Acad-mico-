package utp.ac.pa.sistema.utils;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class IOUtils {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static String ask(String title, String message){ return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE); }
    public static void info(String title, String message){ JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE); }
    public static void warn(String title, String message){ JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE); }
    public static String readLineConsole(String prompt){
        try{ System.out.print(prompt + ": "); return br.readLine(); } catch(IOException e){ return ""; }
    }
    public static int toInt(String s, int def){ try{ return Integer.parseInt(s.trim()); }catch(Exception e){ return def; } }
    public static double toDouble(String s, double def){ try{ return Double.parseDouble(s.trim()); }catch(Exception e){ return def; } }
}