package utp.ac.pa.sistema.utils;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * Clase de utilidades de entrada/salida.
 * Envuelve JOptionPane y BufferedReader para reutilizar código.
 */
public class IOUtils {

    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    /**
     * Método genérico para pedir un dato al usuario.
     * Intenta usar JOptionPane y, si no es posible, cae a consola.
     */
    public static String ask(String titulo, String mensaje) {
        try {
            String input = JOptionPane.showInputDialog(
                    null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                // Usuario canceló: devolvemos cadena vacía para no romper la ejecución
                return "";
            }
            return input.trim();
        } catch (Throwable t) {
            // Si por alguna razón no hay entorno gráfico, usamos consola
            return readLineConsole(mensaje + ": ");
        }
    }

    /**
     * Pide una cadena no vacía.
     */
    public static String askNonEmpty(String titulo, String mensaje) {
        String valor;
        do {
            valor = ask(titulo, mensaje);
            if (valor == null || valor.isBlank()) {
                warn("Dato obligatorio", "El campo no puede estar vacío.");
            }
        } while (valor == null || valor.isBlank());
        return valor;
    }

    /**
     * Pide un texto que sólo contenga letras y espacios.
     */
    public static String askSoloLetras(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            if (!valor.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                warn("Formato inválido", "Solo se permiten letras y espacios.");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide una cadena que sólo contenga dígitos (útil para teléfonos, cédulas simples, etc.).
     */
    public static String askSoloDigitos(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            if (!valor.matches("\\d+")) {
                warn("Formato inválido", "Solo se permiten números (0-9).");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide un correo electrónico con una validación sencilla.
     */
    public static String askEmail(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            // Validación simple: debe contener una sola '@' y al menos un punto después
            if (!valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                warn("Email inválido",
                        "Ingrese un correo electrónico válido (ej: usuario@dominio.com).");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide un número entero (int) válido.
     */
    public static int askInt(String titulo, String mensaje) {
        while (true) {
            String s = askNonEmpty(titulo, mensaje);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                warn("Número inválido", "Debe ingresar un número entero.");
            }
        }
    }

    /**
     * Pide un número decimal (double) válido.
     */
    public static double askDouble(String titulo, String mensaje) {
        while (true) {
            String s = askNonEmpty(titulo, mensaje);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                warn("Número inválido",
                        "Debe ingresar un número válido (use punto decimal si es necesario).");
            }
        }
    }

    /**
     * Pide una fecha en formato ISO (yyyy-MM-dd).
     */
    public static LocalDate askFecha(String titulo, String mensaje) {
        while (true) {
            String s = askNonEmpty(titulo, mensaje + " (formato: yyyy-MM-dd)");
            try {
                return LocalDate.parse(s);
            } catch (Exception e) {
                warn("Fecha inválida",
                        "La fecha debe tener el formato yyyy-MM-dd, por ejemplo 2025-12-01.");
            }
        }
    }

    /**
     * Lee una línea desde consola (fallback cuando no se puede usar GUI).
     */
    public static String readLineConsole(String mensaje) {
        System.out.print(mensaje);
        try {
            String s = br.readLine();
            return s == null ? "" : s.trim();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Conversión segura de String a double con valor por defecto.
     * (Se mantiene por compatibilidad, pero se recomienda usar askDouble).
     */
    public static double toDouble(String s, double def) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Muestra un mensaje informativo.
     */
    public static void info(String titulo, String mensaje) {
        try {
            JOptionPane.showMessageDialog(
                    null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
        } catch (Throwable t) {
            System.out.println("[" + titulo + "] " + mensaje);
        }
    }

    /**
     * Muestra un mensaje de advertencia / error.
     */
    public static void warn(String titulo, String mensaje) {
        try {
            JOptionPane.showMessageDialog(
                    null, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
        } catch (Throwable t) {
            System.err.println("[" + titulo + "] " + mensaje);
        }
    }
}
