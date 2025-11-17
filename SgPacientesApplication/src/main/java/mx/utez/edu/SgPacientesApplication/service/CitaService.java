package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import mx.utez.edu.SgPacientesApplication.structures.Cola;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    private final List<Cita> citas = new ArrayList<>();
    private final Cola<String> colaAtencion = new Cola<>(); // guarda pacienteCurp por orden de llegada
    private long seq = 1L;

    public Cita save(Cita c) {
        if (c.getId() == null) c.setId(seq++);
        if (c.getEstado() == null) c.setEstado("SOLICITADA");
        citas.add(c);
        if (c.getPacienteCurp() != null) colaAtencion.enqueue(c.getPacienteCurp());
        return c;
    }

    public List<Cita> findAll() {
        return new ArrayList<>(citas);
    }

    public Cita atenderSiguiente() {
        String curp = colaAtencion.dequeue();
        if (curp == null) return null;
        for (Cita c : citas) {
            if (curp.equals(c.getPacienteCurp()) && "SOLICITADA".equalsIgnoreCase(c.getEstado())) {
                c.setEstado("ATENDIDA");
                // registrar historial (si se integra con paciente service, usarlo)
                return c;
            }
        }
        return null;
    }

    public int colaSize() { return colaAtencion.size(); }
}
