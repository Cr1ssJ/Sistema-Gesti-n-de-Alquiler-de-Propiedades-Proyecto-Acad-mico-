package utp.ac.pa.sistema.domain;

public class Inquilino {
    private String id;
    private String nombre;
    private String email;
    private String telefono;

    public Inquilino(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre(){ return nombre; }
}
