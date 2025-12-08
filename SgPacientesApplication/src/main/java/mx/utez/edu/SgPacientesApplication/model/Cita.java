package mx.utez.edu.SgPacientesApplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;
    @Getter @Setter private String pacienteCurp;
    @Getter private String departamento;
    @Getter private String fecha; // ISO datetime e.g. 2025-11-20T10:30
    @Getter private LocalTime hora;
    @Getter private String motivo;
    @Getter @Setter
    private String estado; // SOLICITADA, ATENDIDA, CANCELADA

    public Cita() {}


    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", pacienteCurp='" + pacienteCurp + '\'' +
                ", departamento='" + departamento + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora=" + hora +
                ", motivo='" + motivo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
