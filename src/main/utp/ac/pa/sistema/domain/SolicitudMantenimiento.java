package utp.ac.pa.sistema.domain;

import java.time.LocalDate;

public class SolicitudMantenimiento {
    public enum Estado { REGISTRADA, EN_PROCESO, RESUELTA }
    private String id;
    private Propiedad propiedad;
    private Inquilino solicitadoPor;
    private String descripcion;
    private LocalDate fechaSolicitud = LocalDate.now();
    private Estado estado = Estado.REGISTRADA;
    private Tecnico asignado;

    public SolicitudMantenimiento(String id, Propiedad propiedad, Inquilino solicitadoPor, String descripcion) {
        this.id = id;
        this.propiedad = propiedad;
        this.solicitadoPor = solicitadoPor;
        this.descripcion = descripcion;
    }

    public void asignarTecnico(Tecnico t){ this.asignado = t; this.estado = Estado.EN_PROCESO; }
    public void marcarResuelta(){ this.estado = Estado.RESUELTA; }
}
