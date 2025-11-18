package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import mx.utez.edu.SgPacientesApplication.structures.Pila;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final ListaSimple<Paciente> pacientes = new ListaSimple<>(); // lista de pacientes
    private final Map<String, Paciente> mapPorCurp = new HashMap<>(); // mapa por CURP
    private final Map<String, List<HistorialEntry>> historiales = new HashMap<>(); // historiales por CURP
    private final Pila<HistorialEntry> pilaGlobal = new Pila<>(); // pila global
    private long seq = 1L; // id incremental

    public Paciente save(Paciente p) {
        if (p.getId() == null) p.setId(seq++); // asigna id


        Paciente prev = mapPorCurp.get(p.getCurp()); // busca previo
        if (prev != null) {
            pacientes.remove(prev); // elimina previo
        }


        pacientes.add(p); // agrega paciente
        mapPorCurp.put(p.getCurp(), p); // actualiza mapa


        HistorialEntry e = new HistorialEntry(p.getCurp(), LocalDateTime.now(), "Paciente registrado/actualizado"); // crea historial
        historiales.computeIfAbsent(p.getCurp(), k -> new ArrayList<>()).add(e); // agrega historial
        pilaGlobal.push(e); // agrega a pila global
        return p; // devuelve
    }

    public Paciente findByCurp(String curp) {
        return mapPorCurp.get(curp); // busca por CURP
    }

    public Map<String, Object> findAll() {
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("listaPacientes", pacientes);
        return mapResponse; // devuelve copia
    }

    public boolean deleteById(Long id) {
        Optional<Paciente> opt = pacientes.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst(); // busca
        if (opt.isEmpty()) return false; // no encontrado
        Paciente p = opt.get();
        pacientes.remove(p); // elimina
        mapPorCurp.remove(p.getCurp()); // elimina del mapa
        historiales.remove(p.getCurp()); // elimina historial
        HistorialEntry e = new HistorialEntry(p.getCurp(), LocalDateTime.now(), "Paciente eliminado"); // registra eliminación
        pilaGlobal.push(e); // agrega a pila
        System.out.println(pacientes);
        return true; // éxito
    }

    public List<HistorialEntry> getHistorial(String curp) {
        return historiales.getOrDefault(curp, Collections.emptyList()) // obtiene lista
                .stream()
                .sorted(Comparator.comparing(HistorialEntry::getFecha).reversed()) // ordena desc
                .collect(Collectors.toList()); // retorna lista
    }

    public HistorialEntry popUltimoHistorialDemo() {
        return pilaGlobal.pop(); // saca último
    }
}
