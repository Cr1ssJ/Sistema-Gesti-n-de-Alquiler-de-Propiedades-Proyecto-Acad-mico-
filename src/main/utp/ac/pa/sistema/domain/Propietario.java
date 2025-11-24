package utp.ac.pa.sistema.domain;

/**
 * Representa al dueño de una o varias propiedades.
 */
public class Propietario extends Usuario {

    private String email;

    public Propietario(String id, String nombreUsuario, String email) {
        super(id, nombreUsuario, "PROPIETARIO");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nombreUsuario + " (" + email + ")";
    }
}
