package utp.ac.pa.sistema.domain;

/**
 * Clase base para todos los usuarios del sistema:
 * Propietario, Inquilino, Técnico, Administrador.
 */
public abstract class Usuario {

    private String id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String nombreUsuario;
    private String contrasena;
    private String rol; // Ejemplo: "PROPIETARIO", "INQUILINO", etc.

    /**
     * Constructor protegido (solo se usa desde las subclases).
     */
    protected Usuario(String id, String nombreCompleto, String email, String telefono,
                      String nombreUsuario, String contrasena, String rol)
            throws ValidacionException {
        setId(id);
        setNombreCompleto(nombreCompleto);
        setEmail(email);
        setTelefono(telefono);
        setNombreUsuario(nombreUsuario);
        setContrasena(contrasena);
        setRol(rol);
    }

    // Validación genérica de texto
    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws ValidacionException {
        validarTextoObligatorio(id, "id de usuario");
        this.id = id.trim();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) throws ValidacionException {
        validarTextoObligatorio(nombreCompleto, "nombre completo");
        this.nombreCompleto = nombreCompleto.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ValidacionException {
        validarTextoObligatorio(email, "email");
        if (!email.contains("@")) {
            throw new ValidacionException("El email no es válido.");
        }
        this.email = email.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws ValidacionException {
        validarTextoObligatorio(telefono, "teléfono");
        this.telefono = telefono.trim();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) throws ValidacionException {
        validarTextoObligatorio(nombreUsuario, "nombre de usuario");
        this.nombreUsuario = nombreUsuario.trim();
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) throws ValidacionException {
        validarTextoObligatorio(contrasena, "contraseña");
        if (contrasena.length() < 6) {
            throw new ValidacionException("La contraseña debe tener al menos 6 caracteres.");
        }
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) throws ValidacionException {
        validarTextoObligatorio(rol, "rol");
        this.rol = rol.trim().toUpperCase();
    }

    /**
     * Verifica si las credenciales coinciden con este usuario.
     */
    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        return this.nombreUsuario.equals(nombreUsuario)
                && this.contrasena.equals(contrasena);
    }
}
