package utp.ac.pa.sistema.domain;

/**
 * Usuario de tipo Propietario (dueño de propiedades).
 */
public class Propietario extends Usuario {

    public Propietario(String id, String nombreCompleto, String email, String telefono,
                       String nombreUsuario, String contrasena)
            throws ValidacionException {
        super(id, nombreCompleto, email, telefono, nombreUsuario, contrasena, "PROPIETARIO");
    }
}
