package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;

/**
 * Representa una notificación enviada a un usuario del sistema.
 * Ejemplo: recordatorio de pago, aviso de mantenimiento, etc.
 */
public class Notificacion {

    private String idNotificacion;
    private Usuario usuarioDestino;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean leida;

    public Notificacion(String idNotificacion, Usuario usuarioDestino, String mensaje)
            throws ValidacionException {
        setIdNotificacion(idNotificacion);
        setUsuarioDestino(usuarioDestino);
        setMensaje(mensaje);
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) throws ValidacionException {
        validarTextoObligatorio(idNotificacion, "id de notificación");
        this.idNotificacion = idNotificacion.trim();
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) throws ValidacionException {
        if (usuarioDestino == null) {
            throw new ValidacionException("El usuario destino es obligatorio.");
        }
        this.usuarioDestino = usuarioDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) throws ValidacionException {
        validarTextoObligatorio(mensaje, "mensaje");
        this.mensaje = mensaje.trim();
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public boolean isLeida() {
        return leida;
    }

    /**
     * Marca la notificación como leída.
     */
    public void marcarComoLeida() {
        this.leida = true;
    }
}
