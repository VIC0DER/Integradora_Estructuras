package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.Doctor;
import mx.utez.edu.SgPacientesApplication.service.DoctorService;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctores")
@CrossOrigin(origins = "*")
public class DoctorController {
    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping("")
    public ResponseEntity<Object> obtenerTodos() {
        return new ResponseEntity<>(doctorService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/disponibles")
    public ResponseEntity<Object> disponiblesUrgencias() {
        return new ResponseEntity<>(doctorService.findForUrgencies(), HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Object> insertar(@RequestBody Doctor doctor) {
        if(doctor.getNombre().isBlank() || doctor.getEspecialidad().isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(doctorService.save(doctor), HttpStatus.OK);
    }
    @PatchMapping("/{id}/{disponible}")
    public ResponseEntity<Object> actualizarDisponibilidad(@PathVariable Long id, @PathVariable Integer disponible){
        MyHashMap<String, Object> response = doctorService.updateDisponible(id, disponible);
        int code = (int) response.get("code");
        return new ResponseEntity<>(response, code == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
