package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

import utp.ac.pa.sistema.utils.IOUtils;

/**
 * Representa una propiedad que se alquila (casa, apartamento, oficina).
 */
public class Propiedad {

    private String idPropiedad;
    private Direccion direccion;
    private TipoPropiedad tipo;
    private double metrosCuadrados;
    private double precioMensualBase;
    private Propietario propietario;
    private List<ServicioIncluido> serviciosIncluidos;
    private boolean disponible;

    public Propiedad(String idPropiedad, Direccion direccion, TipoPropiedad tipo,
                     double metrosCuadrados, double precioMensualBase,
                     Propietario propietario) throws ValidacionException {

        setIdPropiedad(idPropiedad);
        setDireccion(direccion);
        setTipo(tipo);
        setMetrosCuadrados(metrosCuadrados);
        setPrecioMensualBase(precioMensualBase);
        setPropietario(propietario);
        this.serviciosIncluidos = new ArrayList<>();
        this.disponible = true; // Por defecto, la propiedad inicia disponible
    }

    private void validarTextoObligatorio(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
    }

    public String getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(String idPropiedad) throws ValidacionException {
        validarTextoObligatorio(idPropiedad, "id de propiedad");
        this.idPropiedad = idPropiedad.trim();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) throws ValidacionException {
        if (direccion == null) {
            throw new ValidacionException("La dirección es obligatoria.");
        }
        this.direccion = direccion;
    }

    public TipoPropiedad getTipo() {
        return tipo;
    }

    public void setTipo(TipoPropiedad tipo) throws ValidacionException {
        if (tipo == null) {
            throw new ValidacionException("El tipo de propiedad es obligatorio.");
        }
        this.tipo = tipo;
    }

    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(double metrosCuadrados) throws ValidacionException {
        if (metrosCuadrados <= 0) {
            throw new ValidacionException("Los metros cuadrados deben ser mayores a cero.");
        }
        this.metrosCuadrados = metrosCuadrados;
    }

    public double getPrecioMensualBase() {
        return precioMensualBase;
    }

    public void setPrecioMensualBase(double precioMensualBase) throws ValidacionException {
        if (precioMensualBase <= 0) {
            throw new ValidacionException("El precio mensual debe ser mayor a cero.");
        }
        this.precioMensualBase = precioMensualBase;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) throws ValidacionException {
        if (propietario == null) {
            throw new ValidacionException("La propiedad debe tener un propietario.");
        }
        this.propietario = propietario;
    }

    public List<ServicioIncluido> getServiciosIncluidos() {
        return new ArrayList<>(serviciosIncluidos);
    }

    /**
     * Agrega un servicio incluido en el alquiler.
     */
    public void agregarServicioIncluido(ServicioIncluido servicio) throws ValidacionException {
        if (servicio == null) {
            throw new ValidacionException("El servicio incluido no puede ser nulo.");
        }
        this.serviciosIncluidos.add(servicio);
    }

    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Cambia el estado de disponibilidad de la propiedad.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Calcula el precio total mensual (base + servicios incluidos).
     */
    public double calcularPrecioTotalMensual() {
        double total = precioMensualBase;
        for (ServicioIncluido s : serviciosIncluidos) {
            total += s.getCostoMensual();
        }
        return total;
    }

    /**
     * Flujo interactivo para registrar una propiedad.
     */
    public static void registrarPropiedad(List<Propiedad> propiedades) {
        try {
            String id = IOUtils.askNonEmpty("Registrar propiedad", "ID de la propiedad");
            if (propiedades.stream().anyMatch(p -> p.getIdPropiedad().equalsIgnoreCase(id))) {
                IOUtils.warn("Duplicado", "Ya existe una propiedad con ese ID.");
                return;
            }

            String pais = IOUtils.askNonEmpty("Direccion", "Pais");
            String provincia = IOUtils.askNonEmpty("Direccion", "Provincia");
            String ciudad = IOUtils.askNonEmpty("Direccion", "Ciudad");
            String detalle = IOUtils.askNonEmpty("Direccion", "Direccion detallada");
            Direccion direccion = new Direccion(pais, provincia, ciudad, detalle);

            String tipoTxt = IOUtils.askNonEmpty("Tipo de propiedad",
                    "Tipo (CASA, APARTAMENTO, OFICINA, LOCAL)");
            TipoPropiedad tipo = TipoPropiedad.fromString(tipoTxt);

            double metros = IOUtils.askDouble("Propiedad", "Metros cuadrados");
            double precioBase = IOUtils.askPositiveDouble("Propiedad", "Precio mensual base");

            // Datos basicos del propietario
            String propietarioId = IOUtils.askCedula("Propietario", "ID del propietario");
            String propietarioNombre = IOUtils.askSoloLetras("Propietario", "Nombre completo");
            String propietarioEmail = IOUtils.askEmail("Propietario", "Email");
            String propietarioTelefono = IOUtils.askSoloDigitosMinLength("Propietario", "Telefono", 8);
            String propietarioUsuario = IOUtils.askNonEmpty("Propietario", "Nombre de usuario");
            String propietarioPass = IOUtils.askNonEmpty("Propietario", "Contrasena (min 6 chars)");

            boolean idDupe = propiedades.stream().anyMatch(p ->
                    p.getPropietario().getId().equalsIgnoreCase(propietarioId));
            boolean emailDupe = propiedades.stream().anyMatch(p ->
                    p.getPropietario().getEmail().equalsIgnoreCase(propietarioEmail));
            boolean telDupe = propiedades.stream().anyMatch(p ->
                    p.getPropietario().getTelefono().equals(propietarioTelefono));
            boolean userDupe = propiedades.stream().anyMatch(p ->
                    p.getPropietario().getNombreUsuario().equalsIgnoreCase(propietarioUsuario));
            if (idDupe || emailDupe || telDupe || userDupe) {
                IOUtils.warn("Duplicado",
                        "Ya existe un propietario con alguno de los datos (cedula/email/telefono/usuario).");
                return;
            }
            Propietario propietario = new Propietario(
                    propietarioId, propietarioNombre, propietarioEmail,
                    propietarioTelefono, propietarioUsuario, propietarioPass);

            Propiedad nueva = new Propiedad(
                    id, direccion, tipo, metros, precioBase, propietario);

            // Agregar servicios incluidos (opcional)
            while (true) {
                String agregar = IOUtils.askNonEmpty("Propiedad", "¿Desea agregar un servicio incluido? (s/n)");
                if (agregar.equalsIgnoreCase("s")) {
                    try {
                        String nombreServ = IOUtils.askSoloLetras("Servicio", "Nombre del servicio");
                        String descServ = IOUtils.askNonEmpty("Servicio", "Descripcion del servicio");
                        double costoServ = IOUtils.askPositiveDouble("Servicio", "Costo mensual del servicio");
                        ServicioIncluido servicio = new ServicioIncluido(nombreServ, descServ, costoServ);
                        nueva.agregarServicioIncluido(servicio);
                        IOUtils.info("Servicio agregado", "Se agrego el servicio: " + nombreServ);
                    } catch (ValidacionException e) {
                        IOUtils.warn("Error", e.getMessage());
                    }
                } else if (agregar.equalsIgnoreCase("n")) {
                    break;
                } else {
                    IOUtils.warn("Opcion invalida", "Responda 's' para si o 'n' para no.");
                }
            }

            propiedades.add(nueva);
            IOUtils.info("OK", "Propiedad registrada con exito.");
        } catch (ValidacionException e) {
            IOUtils.warn("Error", e.getMessage());
        }
    }

    /**
     * Lista propiedades disponibles.
     */
    public static void listarDisponibles(List<Propiedad> propiedades) {
        if (propiedades == null || propiedades.isEmpty()) {
            IOUtils.info("Propiedades", "No hay propiedades registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Propiedad p : propiedades) {
            if (p.isDisponible()) {
                sb.append("ID: ").append(p.getIdPropiedad())
                        .append(" | Tipo: ").append(p.getTipo())
                        .append(" | Direccion: ").append(p.getDireccion())
                        .append(" | Precio total: ").append(p.calcularPrecioTotalMensual())
                        .append("\n");
            }
        }
        if (sb.length() == 0) {
            IOUtils.info("Propiedades", "No hay propiedades disponibles.");
        } else {
            IOUtils.info("Propiedades disponibles", sb.toString());
        }
    }
}
