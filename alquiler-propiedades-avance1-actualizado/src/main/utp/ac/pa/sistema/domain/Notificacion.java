package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;

public class Notificacion {
    private String id;
    private String usuarioId;
    private String mensaje;
    private LocalDateTime fecha = LocalDateTime.now();
    private boolean leida = false;

    public Notificacion(String id, String usuarioId, String mensaje) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mensaje = mensaje;
    }

    public void marcarLeida(){ this.leida = true; }
}
