package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.List;

/**
 * Representa a un inquilino del sistema.
 * Extiende Usuario y además incluye email y teléfono.
 */
public class Inquilino extends Usuario {

    private String email;
    private String telefono;

    public Inquilino(String id, String nombre, String email, String telefono) {
        super(id, nombre, "INQUILINO");
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombreUsuario;
    }

    /**
     * Método estático de "caso de uso".
     * Pide los datos con JOptionPane,
     * crear el Inquilino y agregarlo a la lista recibida.
     */
    public static void registrarInquilino(List<Inquilino> lista) {
        String id = IOUtils.ask("Inquilino", "ID:");
        String nombre = IOUtils.ask("Inquilino", "Nombre:");
        String email = IOUtils.ask("Inquilino", "Email:");
        String tel = IOUtils.ask("Inquilino", "Teléfono:");

        Inquilino i = new Inquilino(id, nombre, email, tel);
        lista.add(i);

        IOUtils.info("OK", "Inquilino registrado:\n" + i);
    }

    @Override
    public String toString() {
        return nombreUsuario + " (" + email + ", " + telefono + ")";
    }
}
