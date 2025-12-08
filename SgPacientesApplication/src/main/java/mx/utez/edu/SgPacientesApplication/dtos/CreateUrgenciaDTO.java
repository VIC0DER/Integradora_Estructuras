package mx.utez.edu.SgPacientesApplication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateUrgenciaDTO {
    @Getter @Setter
    private String pacienteCurp;
    @Getter @Setter
    private Long doctorId;
    @Getter @Setter
    private int prioridad;
    @Getter @Setter
    private boolean atendida;
    @Getter @Setter
    private LocalDateTime fechaRegistro;
}
