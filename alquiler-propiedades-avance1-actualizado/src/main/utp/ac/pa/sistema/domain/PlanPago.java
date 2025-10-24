package utp.ac.pa.sistema.domain;

import java.time.LocalDate;

public class PlanPago {
    private String contratoId;
    private LocalDate proximoVencimiento;
    private int diaCorte; // por ejemplo 5 de cada mes

    public PlanPago(String contratoId, int diaCorte) {
        this.contratoId = contratoId;
        this.diaCorte = diaCorte;
    }
}
