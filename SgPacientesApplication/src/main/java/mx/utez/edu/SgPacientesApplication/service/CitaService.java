package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.structures.Cola;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {

    private final List<Cita> citas = new ArrayList<>(); // lista de citas
    private final Cola<String> colaAtencion = new Cola<>(); // cola de atención por curp
    private long seq = 1L; // secuencia de ids

    public Cita save(Cita c) {
        if (c.getId() == null) c.setId(seq++); // asigna id
        if (c.getEstado() == null) c.setEstado("SOLICITADA"); // estado inicial
        citas.add(c); // agrega a lista
        if (c.getPacienteCurp() != null) colaAtencion.enqueue(c.getPacienteCurp()); // agrega a cola
        return c; // devuelve cita
    }

    public List<Cita> findAll() {
        return new ArrayList<>(citas); // devuelve copia de lista
    }

    public Cita atenderSiguiente() {
        String curp = colaAtencion.dequeue(); // obtiene siguiente curp
        if (curp == null) return null; // si no hay
        for (Cita c : citas) {
            if (curp.equals(c.getPacienteCurp()) && "SOLICITADA".equalsIgnoreCase(c.getEstado())) {
                c.setEstado("ATENDIDA"); // marca atendida
                return c; // devuelve cita atendida
            }
        }
        return null; // no encontrada
    }
    public int colaSize() { return colaAtencion.size(); } // tamaño de la cola
}
