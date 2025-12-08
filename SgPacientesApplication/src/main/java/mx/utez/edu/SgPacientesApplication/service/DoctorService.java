package mx.utez.edu.SgPacientesApplication.service;

import mx.utez.edu.SgPacientesApplication.component.MemoryStore;
import mx.utez.edu.SgPacientesApplication.model.Doctor;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    MemoryStore memoryStore;
    public DoctorService(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }
    public MyHashMap<String, Object> findAll(){
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        mapResponse.put("listaDoctores", memoryStore.getDoctores());
        return mapResponse;
    }
    public MyHashMap<String, Object> findForUrgencies(){
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        mapResponse.put("listaDoctores", memoryStore.getDoctoresUrgencias());
        return mapResponse;
    }
    public MyHashMap<String, Object> save(Doctor doctor) {
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        memoryStore.guardarDoctor(doctor);
        mapResponse.put("message", "Registro guardado exitosamente");
        return mapResponse;
    }
    public MyHashMap<String, Object> updateDisponible(Long id, Integer disponible) {
        MyHashMap<String, Object> mapResponse = new MyHashMap<>();
        Doctor doctor = memoryStore.fetchDoctor(id);
        if(doctor == null) {
            mapResponse.put("message", "No se pudo encontrar al doctor");
            mapResponse.put("code", 404);
        } else {
            memoryStore.actualizarDisponible(doctor, disponible);
            mapResponse.put("message", "Disponibilidad actualizada con éxito");
            mapResponse.put("code", 200);
        }
        return mapResponse;
    }
}
