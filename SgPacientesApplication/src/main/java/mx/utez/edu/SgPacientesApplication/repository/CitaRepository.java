package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    ListaSimple<Cita> findByOrderByFechaDescHoraAsc();
    @Query("SELECT c FROM Cita c WHERE c.fecha = :fecha AND c.estado = :estado ORDER BY c.fecha DESC, c.hora ASC")
    ListaSimple<Cita> findByFechaOrderByHoraAsc(
            @Param("fecha") String fecha,
            @Param("estado") String estado
    );
    @Query("SELECT c FROM Cita c WHERE c.pacienteCurp = :curp ORDER BY c.fecha DESC, c.hora DESC")
    ListaSimple<Cita> findByCurp(@Param("curp") String curp);
}
