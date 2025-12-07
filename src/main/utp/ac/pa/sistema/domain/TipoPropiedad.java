package utp.ac.pa.sistema.domain;

/**
 * Tipos básicos de propiedades que maneja el sistema.
 */
public enum TipoPropiedad {
    CASA,
    APARTAMENTO,
    OFICINA,
    LOCAL;

    /**
     * Convierte una cadena a TipoPropiedad, ignorando mayúsculas/minúsculas.
     */
    public static TipoPropiedad fromString(String valor) throws ValidacionException {
        if (valor == null) {
            throw new ValidacionException("El tipo de propiedad es obligatorio.");
        }
        try {
            return TipoPropiedad.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidacionException("Tipo de propiedad inválido. Use: CASA, APARTAMENTO, OFICINA o LOCAL.");
        }
    }
}
