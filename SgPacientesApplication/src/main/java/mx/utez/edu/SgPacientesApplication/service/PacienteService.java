package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.component.MemoryStore;
import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import mx.utez.edu.SgPacientesApplication.structures.Pila;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    private MemoryStore memoryStore;

    private final Map<String, List<HistorialEntry>> historiales = new HashMap<>(); // historiales por CURP
    private final Pila<HistorialEntry> pilaGlobal = new Pila<>(); // pila global

    public PacienteService(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }

    public Paciente save(Paciente p) {
        memoryStore.guardarPaciente(p);
        return p; // devuelve
    }

    public Paciente findByCurp(String curp) {
        return memoryStore.getMapPorCurp().get(curp.toLowerCase()); // busca por CURP
    }

    public MyHashMap<String, Object> findAll() {
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        mapResponse.put("listaPacientes", memoryStore.getPacientes());
        return mapResponse; // devuelve copia
    }

    public boolean deleteById(Long id) {
        ListaSimple<Paciente> pacientes = memoryStore.getPacientes();
        Optional<Paciente> opt = pacientes.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst(); // busca
        if (opt.isEmpty()) return false; // no encontrado
        Paciente p = opt.get();
        memoryStore.eliminarPaciente(p);
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
