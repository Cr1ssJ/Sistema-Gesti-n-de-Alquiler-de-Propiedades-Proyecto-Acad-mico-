package utp.ac.pa.sistema.domain;

/**
 * Clase base para todos los tipos de usuarios del sistema.
 * (Administrador, Inquilino, Propietario, Tecnico)
 */
public class Usuario {

    protected String id;
    protected String nombreUsuario;
    protected String rol;

    public Usuario(String id, String nombreUsuario, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    /**
     * Verifica si el usuario tiene un rol específico (ej. "ADMIN").
     */
    public boolean tieneRol(String r) {
        return rol != null && rol.equalsIgnoreCase(r);
    }

    @Override
    public String toString() {
        return nombreUsuario + " (" + rol + ")";
    }
}
