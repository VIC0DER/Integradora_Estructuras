package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;

@Repository
public class CitaRepository {

    // Cola FIFO para manejo de citas
    private final Queue<Cita> citas = new LinkedList<>();

    // Agregar una cita al final de la cola
    public Cita save(Cita c) {
        citas.offer(c);  // agrega al final
        return c;
    }

    // Ver la siguiente cita sin eliminarla
    public Cita peekNext() {
        return citas.peek(); // null si está vacío
    }

    // Atender una cita (sacar la primera en entrar)
    public Cita pollNext() {
        return citas.poll();
    }

    // Obtener todas las citas (como lista)
    public Queue<Cita> findAll() {
        return citas;
    }

    // Cancelar una cita por ID
    public void deleteById(Long id) {
        citas.removeIf(c -> c.getId().equals(id));
    }
}
