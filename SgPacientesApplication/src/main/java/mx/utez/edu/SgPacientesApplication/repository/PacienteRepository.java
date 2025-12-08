package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCurp(String curp);
}
