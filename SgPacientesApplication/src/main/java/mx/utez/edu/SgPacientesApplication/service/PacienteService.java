package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.structures.Pila;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final Map<String, Paciente> mapPorCurp = new HashMap<>();
    private final Map<String, List<HistorialEntry>> historiales = new HashMap<>();
    private final Pila<HistorialEntry> pilaGlobal = new Pila<>();
    private long seq = 1L;

    public Paciente save(Paciente p) {
        if (p.getId() == null) p.setId(seq++);

        // Si curp ya existe, actualizar (remover versión anterior)
        Paciente prev = mapPorCurp.get(p.getCurp());
        if (prev != null) {
            pacientes.remove(prev);
        }

        pacientes.add(p);
        mapPorCurp.put(p.getCurp(), p);

        HistorialEntry e = new HistorialEntry(p.getCurp(), LocalDateTime.now(), "Paciente registrado/actualizado");
        historiales.computeIfAbsent(p.getCurp(), k -> new ArrayList<>()).add(e);
        pilaGlobal.push(e);
        return p;
    }

    public Paciente findByCurp(String curp) {
        return mapPorCurp.get(curp);
    }

    public List<Paciente> findAll() {
        return new ArrayList<>(pacientes);
    }

    public boolean deleteById(Long id) {
        Optional<Paciente> opt = pacientes.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
        if (opt.isEmpty()) return false;
        Paciente p = opt.get();
        pacientes.remove(p);
        mapPorCurp.remove(p.getCurp());
        historiales.remove(p.getCurp());
        HistorialEntry e = new HistorialEntry(p.getCurp(), LocalDateTime.now(), "Paciente eliminado");
        pilaGlobal.push(e);
        return true;
    }

    public List<HistorialEntry> getHistorial(String curp) {
        return historiales.getOrDefault(curp, Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(HistorialEntry::getFecha).reversed())
                .collect(Collectors.toList());
    }

    public HistorialEntry popUltimoHistorialDemo() {
        return pilaGlobal.pop();
    }
}
