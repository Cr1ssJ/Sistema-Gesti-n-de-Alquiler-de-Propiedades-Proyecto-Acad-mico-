package utp.ac.pa.sistema.domain;

import utp.ac.pa.sistema.utils.IOUtils;
import java.util.List;

/**
 * Representa a un inquilino del sistema.
 */
public class Inquilino extends Usuario {

    private String email;
    private String telefono;

    public Inquilino(String id, String nombre, String email, String telefono) {
        super(id, nombre, "INQUILINO");
        this.email = email;
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public static Inquilino buscarPorId(List<Inquilino> lista, String id) {
        for (Inquilino i : lista) {
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }

    public static void registrarInquilino(List<Inquilino> lista) {
        // ID
        String id;
        while (true) {
            id = IOUtils.askNonEmpty("Inquilino", "ID:");
            if (buscarPorId(lista, id) != null) {
                IOUtils.warn("ID duplicado",
                        "Ya existe un inquilino con ese ID. Ingrese uno diferente.");
            } else {
                break;
            }
        }

        String nombre = IOUtils.askSoloLetras("Inquilino", "Nombre (solo letras):");
        String email = IOUtils.askEmail("Inquilino", "Email:");
        String tel = IOUtils.askSoloDigitos("Inquilino", "Teléfono (solo números):");

        Inquilino i = new Inquilino(id, nombre, email, tel);
        lista.add(i);

        IOUtils.info("OK", "Inquilino registrado:\n" + i);
    }

    /**
     * Lista todos los inquilinos registrados.
     */
    public static void listarInquilinos(List<Inquilino> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INQUILINOS REGISTRADOS ===\n");
        if (lista.isEmpty()) {
            sb.append("No hay inquilinos registrados.\n");
        } else {
            for (Inquilino i : lista) {
                sb.append("- Cédula(ID): ").append(i.id)
                  .append(" | Nombre: ").append(i.nombreUsuario)
                  .append(" | Email: ").append(i.email)
                  .append(" | Tel: ").append(i.telefono)
                  .append("\n");
            }
        }
        IOUtils.info("Inquilinos", sb.toString());
    }

    @Override
    public String toString() {
        return nombreUsuario + " (" + email + ", " + telefono + ")";
    }
}
