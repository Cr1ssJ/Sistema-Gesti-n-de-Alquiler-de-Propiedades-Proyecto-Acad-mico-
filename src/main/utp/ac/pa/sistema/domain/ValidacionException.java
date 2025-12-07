package utp.ac.pa.sistema.domain;

/**
 * Excepción de validación para entradas de usuario o reglas de negocio.
 */
public class ValidacionException extends Exception {

    public ValidacionException(String message) {
        super(message);
    }
}
