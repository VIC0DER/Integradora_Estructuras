package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Urgencia;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrgenciaRepository extends JpaRepository<Urgencia, Long> {
    @Query("SELECT u FROM Urgencia u LEFT JOIN u.doctor d WHERE u.atendida = false")
    ListaSimple<Urgencia> getUrgenciaAndDoctor();
    @Query("SELECT u FROM Urgencia u LEFT JOIN u.doctor d WHERE u.atendida = true ORDER BY u.fechaDeAlta ASC")
    ListaSimple<Urgencia> getAtendidas();
}
