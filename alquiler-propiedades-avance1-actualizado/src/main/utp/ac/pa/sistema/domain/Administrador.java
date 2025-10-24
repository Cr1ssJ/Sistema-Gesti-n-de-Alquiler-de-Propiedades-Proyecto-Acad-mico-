package utp.ac.pa.sistema.domain;

public class Administrador extends Usuario {
    public Administrador(String id, String nombreUsuario) {
        super(id, nombreUsuario, "ADMIN");
    }
}
