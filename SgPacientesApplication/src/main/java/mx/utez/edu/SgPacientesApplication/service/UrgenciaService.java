package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.component.MemoryStore;
import mx.utez.edu.SgPacientesApplication.dtos.CreateUrgenciaDTO;
import mx.utez.edu.SgPacientesApplication.model.Doctor;
import mx.utez.edu.SgPacientesApplication.model.Urgencia;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class UrgenciaService {
    private final MemoryStore memoryStore;
    public UrgenciaService(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }
    public MyHashMap<String, Object> save(CreateUrgenciaDTO urgencia) {

        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        Doctor doctor = memoryStore.fetchDoctor(urgencia.getDoctorId());
        urgencia.setFechaRegistro(LocalDateTime.now());
        if(doctor == null){
            mapResponse.put("message", "No se pudo encontrar al doctor.");
            mapResponse.put("code", 404);
        } else if (!doctor.isDisponible()) {
            mapResponse.put("message", "El doctor seleccionado no se encuentra disponible.");
            mapResponse.put("code", 400);
        } else {
            memoryStore.actualizarDisponible(doctor, 0);
            memoryStore.guardarUrgencia(urgencia, doctor);
            mapResponse.put("message", "Registro guardado exitosamente.");
            mapResponse.put("code", 200);
        }
        return mapResponse;
    }

    public MyHashMap<String, Object> findCurrentUrgencies() {
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        Urgencia[] activas = memoryStore.getUrgenciasHeap().getHeap();
        Object[] atendidas = memoryStore.getUrgenciasAtendidas().getArr();
        mapResponse.put("urgenciasActivas", activas);
        mapResponse.put("urgenciasAtendidas", atendidas);
        return mapResponse;
    }

    public MyHashMap<String, Object> attend(Long id) {
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        int code = memoryStore.atenderUrgencia(id);
        mapResponse.put("code",  code);
        return mapResponse;
    }

    public static void main(String[] args){
        System.out.println(LocalDateTime.now());
        System.out.println(Instant.now());
    }
}
