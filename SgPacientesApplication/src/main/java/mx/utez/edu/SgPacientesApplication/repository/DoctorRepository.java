package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Doctor;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.especialidad = :especialidad AND d.disponible = :disponible")
    ListaSimple<Doctor> buscarDisponibles(
            @Param("especialidad") String especialidad,
            @Param("disponible") boolean disponible
    );
}
