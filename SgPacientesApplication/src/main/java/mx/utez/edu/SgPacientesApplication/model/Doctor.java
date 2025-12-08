package mx.utez.edu.SgPacientesApplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String especialidad;
    @Getter @Setter
    private boolean disponible;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
