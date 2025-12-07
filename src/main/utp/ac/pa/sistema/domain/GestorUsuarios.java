package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar los usuarios del sistema:
 * registro, autenticación y búsqueda.
 */
public class GestorUsuarios {

    private List<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Registra un nuevo usuario, validando que no exista otro
     * con el mismo nombre de usuario.
     */
    public void registrarUsuario(Usuario usuario) throws ValidacionException {
        if (usuario == null) {
            throw new ValidacionException("El usuario no puede ser nulo.");
        }
        if (buscarPorNombreUsuario(usuario.getNombreUsuario()) != null) {
            throw new ValidacionException("Ya existe un usuario con ese nombre de usuario.");
        }
        this.usuarios.add(usuario);
    }

    /**
     * Autentica a un usuario por nombre de usuario y contraseña.
     */
    public Usuario autenticar(String nombreUsuario, String contrasena) throws ValidacionException {
        if (nombreUsuario == null || contrasena == null) {
            throw new ValidacionException("Credenciales inválidas.");
        }
        Usuario encontrado = buscarPorNombreUsuario(nombreUsuario);
        if (encontrado != null && encontrado.validarCredenciales(nombreUsuario, contrasena)) {
            return encontrado;
        }
        throw new ValidacionException("Usuario o contraseña incorrectos.");
    }

    /**
     * Busca un usuario por su nombre de usuario.
     */
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Devuelve una copia de la lista de usuarios registrados.
     */
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
