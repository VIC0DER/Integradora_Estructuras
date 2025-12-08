package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.component.MemoryStore;
import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.structures.Cola;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import org.springframework.stereotype.Service;

@Service
public class CitaService {
    private final MemoryStore memoryStore;


    public CitaService(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }

    public Cita save(Cita c) {
        if (c.getEstado() == null) c.setEstado("SOLICITADA"); // estado inicial
        memoryStore.guardarCita(c);
        return c; // devuelve cita
    }

    public MyHashMap<String, Object> findAll() {
        MyHashMap<String, Object> response = new MyHashMap<>();
        ListaSimple<Cita> citas = memoryStore.getCitas();
        Cola<Cita> citasActivas = memoryStore.getCitasActivas();
        Object[] citasActuales = new Object[citasActivas.size()];
        System.arraycopy(
                citasActivas.getArr(),
                citasActivas.getHead(),
                citasActuales, 0,
                citasActuales.length
        );
        response.put("citasActuales", citasActuales);
        response.put("listaCitas", citas);
        return response;
    }

    public MyHashMap<String, Object> atenderSiguiente() {
        MyHashMap<String, Object> response = new MyHashMap<>();
        int code = memoryStore.atenderCita();
        String mensaje = switch(code) {
            case 200 -> "Cita atendida con éxito";
            case 404 -> "Registros no encontrados";
            default -> "Ocurrió un error inesperado";
        };
        response.put("mensaje", mensaje);
        response.put("code", code);
        return response;
    }

    public MyHashMap<String, Object> buscarPorPaciente(Long idPaciente) {
        MyHashMap<String, Object> response = new MyHashMap<>();
        Paciente paciente = memoryStore.fetchPaciente(idPaciente);
        if (paciente != null) {
            response.put("historialCitas", memoryStore.obtenerCitasPorPaciente(paciente));
            response.put("mensaje", "Historial de citas cargado con éxito.");
            response.put("code", 200);
        } else {
            response.put("mensaje", "No se pudo encontrar al paciente.");
            response.put("code", 404);
        }
        return response;
    }
}
