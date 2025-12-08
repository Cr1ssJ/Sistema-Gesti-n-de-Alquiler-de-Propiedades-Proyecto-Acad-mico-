package utp.ac.pa.sistema.utils;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * Utilidades de entrada/salida.
 * Envuelve JOptionPane y consola para reusar codigo.
 */
public class IOUtils {

    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    /**
     * Metodo generico para pedir un dato al usuario.
     * Intenta usar JOptionPane y, si no es posible, cae a consola.
     */
    public static String ask(String titulo, String mensaje) {
        try {
            String input = JOptionPane.showInputDialog(
                    null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                // Usuario cancelo: devolvemos cadena vacia para no romper la ejecucion
                return "";
            }
            return input.trim();
        } catch (Throwable t) {
            // Si no hay entorno grafico, usamos consola
            return readLineConsole(mensaje + ": ");
        }
    }

    /**
     * Pide una cadena no vacia.
     */
    public static String askNonEmpty(String titulo, String mensaje) {
        String valor;
        do {
            valor = ask(titulo, mensaje);
            if (valor == null || valor.isBlank()) {
                warn("Dato obligatorio", "El campo no puede estar vacio.");
            }
        } while (valor == null || valor.isBlank());
        return valor;
    }

    /**
     * Pide un texto que solo contenga letras y espacios.
     */
    public static String askSoloLetras(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            if (!valor.matches("[A-Za-z ]+")) {
                warn("Formato invalido", "Solo se permiten letras y espacios.");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide una cadena que solo contenga digitos (util para telefonos, etc.).
     */
    public static String askSoloDigitos(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            if (!valor.matches("\\d+")) {
                warn("Formato invalido", "Solo se permiten numeros (0-9).");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide un correo electronico con una validacion sencilla.
     */
    public static String askEmail(String titulo, String mensaje) {
        String valor;
        do {
            valor = askNonEmpty(titulo, mensaje);
            // Validacion simple: debe contener una sola '@' y al menos un punto despues
            if (!valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                warn("Email invalido",
                        "Correo electronico invalido. Verifique el formato (ej: usuario@dominio.com).");
                valor = null;
            }
        } while (valor == null);
        return valor;
    }

    /**
     * Pide un numero entero (int) valido.
     */
    public static int askInt(String titulo, String mensaje) {
        while (true) {
            String s = askNonEmpty(titulo, mensaje);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                warn("Numero invalido", "Debe ingresar un numero entero.");
            }
        }
    }

    /**
     * Pide un numero decimal (double) valido.
     */
    public static double askDouble(String titulo, String mensaje) {
        while (true) {
            String s = askNonEmpty(titulo, mensaje);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                warn("Numero invalido",
                        "Debe ingresar un numero valido (use punto decimal si es necesario).");
            }
        }
    }

    /**
     * Pide una cedula con formato x-xxxx-xxxx donde:
     * - primer bloque: 1 a 10
     * - segundo y tercero: 1 a 4 digitos.
     */
    public static String askCedula(String titulo, String mensaje) {
        while (true) {
            String valor = askNonEmpty(titulo, mensaje + " (ej: 8-123-4567)");
            if (valor.matches("(10|[1-9])-\\d{1,4}-\\d{1,4}")) {
                return valor;
            }
            warn("Formato invalido",
                    "Formato de cedula invalido. Ej: 1-2023-2324 (primer bloque 1-10; demas 1-4 digitos).");
        }
    }

    /**
     * Pide solo digitos con una longitud minima.
     */
    public static String askSoloDigitosMinLength(String titulo, String mensaje, int minLength) {
        while (true) {
            String valor = askSoloDigitos(titulo, mensaje);
            if (valor.length() >= minLength) {
                return valor;
            }
            warn("Formato invalido",
                    "El valor debe contener solo numeros y tener al menos " + minLength + " digitos.");
        }
    }

    /**
     * Pide un numero decimal mayor a cero.
     */
    public static double askPositiveDouble(String titulo, String mensaje) {
        while (true) {
            double valor = askDouble(titulo, mensaje);
            if (valor > 0) {
                return valor;
            }
            warn("Numero invalido", "El valor debe ser mayor a cero.");
        }
    }

    /**
     * Pide un entero mayor a cero.
     */
    public static int askPositiveInt(String titulo, String mensaje) {
        while (true) {
            int valor = askInt(titulo, mensaje);
            if (valor > 0) {
                return valor;
            }
            warn("Numero invalido", "El valor debe ser mayor a cero.");
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
                warn("Fecha invalida",
                        "La fecha debe tener el formato yyyy-MM-dd, por ejemplo 2025-12-01.");
            }
        }
    }

    /**
     * Lee una linea desde consola (fallback cuando no se puede usar GUI).
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
     * Conversion segura de String a double con valor por defecto.
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
