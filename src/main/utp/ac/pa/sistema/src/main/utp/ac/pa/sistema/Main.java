package utp.ac.pa.sistema;
import utp.ac.pa.sistema.domain.*; import utp.ac.pa.sistema.utils.IOUtils;
import javax.swing.*; import java.time.LocalDate;
public class Main {
    private static final DataStore db = new DataStore();
    public static void main(String[] args) {
        UIManager.put("OptionPane.okButtonText","Aceptar"); UIManager.put("OptionPane.cancelButtonText","Cancelar");
        boolean running=true;
        while(running){
            String opt = IOUtils.ask("Sistema de Alquiler", menu());
            if(opt==null) break;
            switch(opt.trim()){
                case "1": regPropiedad(); break;
                case "2": regInquilino(); break;
                case "3": crearContrato(); break;
                case "4": regPago(); break;
                case "5": ticketMant(); break;
                case "6": resumen(); break;
                case "0": running=false; break;
                default: IOUtils.warn("Opción inválida","Intenta nuevamente.");
            }
        }
        IOUtils.info("Salida","Gracias por usar el sistema.");
    }
    private static String menu(){ return "1) Registrar Propiedad\n2) Registrar Inquilino\n3) Crear Contrato de Alquiler\n4) Registrar Pago\n5) Solicitud de Mantenimiento (BufferedReader para descripción)\n6) Ver Resumen\n0) Salir\n"; }
    private static void regPropiedad(){
        String id=IOUtils.ask("Propiedad","ID:"); String tipoStr=IOUtils.ask("Propiedad","Tipo (CASA/APARTAMENTO/LOCAL/OFICINA):");
        Propiedad.Tipo tipo=Propiedad.Tipo.valueOf(tipoStr.toUpperCase().trim());
        String ciudad=IOUtils.ask("Dirección","Ciudad:"); String barrio=IOUtils.ask("Dirección","Barrio:");
        String calle=IOUtils.ask("Dirección","Calle:"); String numero=IOUtils.ask("Dirección","Número:");
        Direccion dir=new Direccion("Panamá","Panamá",ciudad,barrio,calle,numero,"0000");
        Propiedad p=new Propiedad(id,tipo,dir); db.propiedades.add(p);
        IOUtils.info("OK","Propiedad registrada: "+p);
    }
    private static void regInquilino(){
        String id=IOUtils.ask("Inquilino","ID:"); String nombre=IOUtils.ask("Inquilino","Nombre:");
        String email=IOUtils.ask("Inquilino","Email:"); String tel=IOUtils.ask("Inquilino","Teléfono:");
        Inquilino i=new Inquilino(id,nombre,email,tel); db.inquilinos.add(i);
        IOUtils.info("OK","Inquilino registrado: "+i);
    }
    private static void crearContrato(){
        if(db.propiedades.isEmpty()||db.inquilinos.isEmpty()){ IOUtils.warn("Faltan datos","Registra una propiedad y un inquilino."); return; }
        String id=IOUtils.ask("Contrato","ID contrato:");
        String propId=IOUtils.ask("Contrato","ID de propiedad:"); Propiedad prop=db.propiedades.stream().filter(p->p.id.equals(propId)).findFirst().orElse(null);
        if(prop==null){ IOUtils.warn("Error","Propiedad no encontrada."); return; }
        String inqId=IOUtils.ask("Contrato","ID de inquilino:"); Inquilino inq=db.inquilinos.stream().filter(i->i.id.equals(inqId)).findFirst().orElse(null);
        if(inq==null){ IOUtils.warn("Error","Inquilino no encontrado."); return; }
        String fi=IOUtils.ask("Contrato","Fecha inicio (YYYY-MM-DD):"); String ff=IOUtils.ask("Contrato","Fecha fin (YYYY-MM-DD):");
        double monto=IOUtils.toDouble(IOUtils.ask("Contrato","Monto mensual:"),0);
        ContratoAlquiler c=new ContratoAlquiler(id,prop,inq,LocalDate.parse(fi),LocalDate.parse(ff),monto); db.contratos.add(c);
        IOUtils.info("OK","Contrato creado: "+c);
    }
    private static void regPago(){
        if(db.contratos.isEmpty()){ IOUtils.warn("Faltan datos","Crea un contrato primero."); return; }
        String contratoId=IOUtils.ask("Pago","ID de contrato:"); ContratoAlquiler c=db.contratos.stream().filter(k->k.id.equals(contratoId)).findFirst().orElse(null);
        if(c==null){ IOUtils.warn("Error","Contrato no encontrado."); return; }
        String id=IOUtils.ask("Pago","ID del pago:"); String fecha=IOUtils.ask("Pago","Fecha (YYYY-MM-DD):");
        double monto=IOUtils.toDouble(IOUtils.ask("Pago","Monto:"),0); String metodo=IOUtils.ask("Pago","Método:");
        Pago p=new Pago(id,contratoId,LocalDate.parse(fecha),monto,metodo); c.registrarPago(p); db.pagos.add(p);
        IOUtils.info("OK","Pago registrado: "+p);
    }
    private static void ticketMant(){
        if(db.propiedades.isEmpty()||db.inquilinos.isEmpty()){ IOUtils.warn("Faltan datos","Registra una propiedad y un inquilino."); return; }
        String id=IOUtils.ask("Ticket","ID del ticket:"); String propId=IOUtils.ask("Ticket","ID de propiedad:");
        Propiedad prop=db.propiedades.stream().filter(p->p.id.equals(propId)).findFirst().orElse(null);
        if(prop==null){ IOUtils.warn("Error","Propiedad no encontrada."); return; }
        String inqId=IOUtils.ask("Ticket","ID de inquilino:"); Inquilino inq=db.inquilinos.stream().filter(i->i.id.equals(inqId)).findFirst().orElse(null);
        if(inq==null){ IOUtils.warn("Error","Inquilino no encontrado."); return; }
        String desc = IOUtils.readLineConsole("Escribe la descripción en la consola (BufferedReader) y presiona Enter");
        if(desc==null||desc.isBlank()) desc="Sin descripción";
        SolicitudMantenimiento sm=new SolicitudMantenimiento(id,prop,inq,desc); db.tickets.add(sm);
        IOUtils.info("OK","Ticket creado: "+sm);
    }
    private static void resumen(){
        StringBuilder sb=new StringBuilder();
        sb.append("Propiedades: ").append(db.propiedades.size()).append("\n")
          .append("Inquilinos: ").append(db.inquilinos.size()).append("\n")
          .append("Contratos: ").append(db.contratos.size()).append("\n")
          .append("Pagos: ").append(db.pagos.size()).append("\n")
          .append("Tickets: ").append(db.tickets.size()).append("\n");
        IOUtils.info("Resumen", sb.toString());
    }
}