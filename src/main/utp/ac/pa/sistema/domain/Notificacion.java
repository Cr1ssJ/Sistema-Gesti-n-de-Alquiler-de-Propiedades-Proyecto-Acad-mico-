package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;

/**
 * Representa una notificación enviada a un usuario
 * (por ejemplo, aviso de pago o de ticket de mantenimiento).
 */
public class Notificacion {

    private String id;
    private String usuarioId;   // id del Usuario destino
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leida;

    public Notificacion(String id, String usuarioId, String mensaje) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    public String getId() { 
        return id; 
    }

    public String getUsuarioId() { 
        return usuarioId; 
    }

    public String getMensaje() { 
        return mensaje; 
    }

    public LocalDateTime getFecha() { 
        return fecha;
     }

    public boolean isLeida() { 
        return leida;
     }

    public void marcarLeida() {
        this.leida = true;
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " + mensaje + " -> usuario " + usuarioId +
                (leida ? " (leída)" : " (no leída)");
    }
}
