package utp.ac.pa.sistema.domain;

/**
 * Usuario de tipo Administrador del sistema.
 */
public class Administrador extends Usuario {

    public Administrador(String id, String nombreCompleto, String email, String telefono,
                         String nombreUsuario, String contrasena)
            throws ValidacionException {
        super(id, nombreCompleto, email, telefono, nombreUsuario, contrasena, "ADMINISTRADOR");
    }
}
