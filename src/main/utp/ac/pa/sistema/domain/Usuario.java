package utp.ac.pa.sistema.domain;

public class Usuario {
    protected String id;
    protected String nombreUsuario;
    protected String rol; // ADMIN, GESTOR, TECNICO

    public Usuario(String id, String nombreUsuario, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public boolean tieneRol(String r){ return rol != null && rol.equalsIgnoreCase(r); }
}
