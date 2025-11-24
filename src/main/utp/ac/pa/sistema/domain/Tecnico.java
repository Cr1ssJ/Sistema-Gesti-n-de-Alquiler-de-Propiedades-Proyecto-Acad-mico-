package utp.ac.pa.sistema.domain;

/**
 * Representa a un técnico que atiende solicitudes de mantenimiento.
 */
public class Tecnico extends Usuario {

    private String especialidad;

    public Tecnico(String id, String nombreUsuario, String especialidad) {
        super(id, nombreUsuario, "TECNICO");
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return nombreUsuario + " - " + especialidad;
    }
}
