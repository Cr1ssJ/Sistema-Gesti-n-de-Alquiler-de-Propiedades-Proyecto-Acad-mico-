package utp.ac.pa.sistema.domain;

/**
 * Clase base para todos los usuarios del sistema:
 * Propietario, Inquilino, Tecnico, Administrador.
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

    // Validacion generica de texto
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
        String limpio = id.trim();
        if (!limpio.matches("(10|[1-9])-\\d{1,4}-\\d{1,4}")) {
            throw new ValidacionException("Formato de cedula invalido. Ejemplo: 1-2023-2324 (primer bloque 1-10; demas 1-4 digitos).");
        }
        this.id = limpio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) throws ValidacionException {
        validarTextoObligatorio(nombreCompleto, "nombre completo");
        String limpio = nombreCompleto.trim();
        if (!limpio.matches("[A-Za-z ]+")) {
            throw new ValidacionException("El nombre no puede estar vacio ni contener caracteres invalidos.");
        }
        this.nombreCompleto = limpio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ValidacionException {
        validarTextoObligatorio(email, "email");
        String limpio = email.trim();
        if (!limpio.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ValidacionException("Correo electronico invalido. Verifique el formato.");
        }
        this.email = limpio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws ValidacionException {
        validarTextoObligatorio(telefono, "telefono");
        String limpio = telefono.trim();
        if (!limpio.matches("\\d{8,}")) {
            throw new ValidacionException("El telefono debe contener solo numeros y tener al menos 8 digitos.");
        }
        this.telefono = limpio;
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
        validarTextoObligatorio(contrasena, "contrasena");
        if (contrasena.length() < 6) {
            throw new ValidacionException("La contrasena debe tener al menos 6 caracteres.");
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
