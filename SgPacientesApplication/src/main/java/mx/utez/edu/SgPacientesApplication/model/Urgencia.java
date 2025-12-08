package mx.utez.edu.SgPacientesApplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urgencia")
public class Urgencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    @Column(length = 18)
    private String pacienteCurp;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Getter @Setter
    private int prioridad;
    @Getter @Setter
    private boolean atendida;
    @Getter @Setter
    private LocalDateTime fechaRegistro;
    @Getter @Setter
    private LocalDateTime fechaDeAlta;
}
