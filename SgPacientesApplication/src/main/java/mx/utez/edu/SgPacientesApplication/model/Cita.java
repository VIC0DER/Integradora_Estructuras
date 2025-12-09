package mx.utez.edu.SgPacientesApplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

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
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(id, cita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
