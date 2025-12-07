package utp.ac.pa.sistema.domain;

import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Usuario de tipo Inquilino (persona que alquila una propiedad).
 */
public class Inquilino extends Usuario {

    public Inquilino(String id, String nombreCompleto, String email, String telefono,
                     String nombreUsuario, String contrasena)
            throws ValidacionException {
        super(id, nombreCompleto, email, telefono, nombreUsuario, contrasena, "INQUILINO");
    }

    /**
     * Flujo interactivo para registrar un inquilino.
     */
    public static void registrarInquilino(List<Inquilino> inquilinos) {
        try {
            String id = IOUtils.askNonEmpty("Registrar inquilino", "ID del inquilino");
            if (inquilinos.stream().anyMatch(i -> i.getId().equalsIgnoreCase(id))) {
                IOUtils.warn("Duplicado", "Ya existe un inquilino con ese ID.");
                return;
            }
            String nombre = IOUtils.askSoloLetras("Registrar inquilino", "Nombre completo");
            String email = IOUtils.askEmail("Registrar inquilino", "Email");
            String telefono = IOUtils.askSoloDigitos("Registrar inquilino", "Telefono");
            String usuario = IOUtils.askNonEmpty("Registrar inquilino", "Nombre de usuario");
            String pass = IOUtils.askNonEmpty("Registrar inquilino", "Contrasena (min 6 chars)");

            Inquilino nuevo = new Inquilino(id, nombre, email, telefono, usuario, pass);
            inquilinos.add(nuevo);
            IOUtils.info("OK", "Inquilino registrado con exito.");
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }

    /**
     * Lista los inquilinos registrados.
     */
    public static void listarInquilinos(List<Inquilino> inquilinos) {
        if (inquilinos == null || inquilinos.isEmpty()) {
            IOUtils.info("Inquilinos", "No hay inquilinos registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Inquilino i : inquilinos) {
            sb.append("ID: ").append(i.getId())
              .append(" | Nombre: ").append(i.getNombreCompleto())
              .append(" | Email: ").append(i.getEmail())
              .append("\n");
        }
        IOUtils.info("Listado de inquilinos", sb.toString());
    }
}
