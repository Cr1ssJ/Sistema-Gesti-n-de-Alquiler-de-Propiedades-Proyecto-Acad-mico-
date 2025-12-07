package utp.ac.pa.sistema.domain;

/**
 * Usuario de tipo Técnico (se le asignan solicitudes de mantenimiento).
 */
public class Tecnico extends Usuario {

    private String especialidad;

    public Tecnico(String id, String nombreCompleto, String email, String telefono,
                   String nombreUsuario, String contrasena, String especialidad)
            throws ValidacionException {
        super(id, nombreCompleto, email, telefono, nombreUsuario, contrasena, "TECNICO");
        setEspecialidad(especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) throws ValidacionException {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new ValidacionException("La especialidad del técnico es obligatoria.");
        }
        this.especialidad = especialidad.trim();
    }
}
