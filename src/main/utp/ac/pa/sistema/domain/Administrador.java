package utp.ac.pa.sistema.domain;

/**
 * Representa a un Administrador del sistema.
 * Extiende la clase base Usuario.
 */
public class Administrador extends Usuario {

    /**
     * Constructor de la clase Administrador.
     *
     * @param id             Identificador único del administrador
     * @param nombreUsuario  Nombre del administrador
     */
    public Administrador(String id, String nombreUsuario) {
        super(id, nombreUsuario, "ADMIN");
    }

    
}
