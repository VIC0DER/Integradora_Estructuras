package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.service.CitaService;
import mx.utez.edu.SgPacientesApplication.service.PacienteService;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import mx.utez.edu.SgPacientesApplication.util.HttpStatusUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {
    private final CitaService citaService; // servicio de citas
    private final PacienteService pacienteService; // servicio de pacientes

    public CitaController(CitaService citaService, PacienteService pacienteService) {
        this.citaService = citaService; // asigna servicio de citas
        this.pacienteService = pacienteService; // asigna servicio de pacientes
    }

    @GetMapping("")
    public ResponseEntity<Object> listar() {
        return new ResponseEntity<>(citaService.findAll(), HttpStatus.OK);
    }
    @GetMapping("{idPaciente}")
    public ResponseEntity<Object> listarPorPaciente(@PathVariable Long idPaciente) {
        MyHashMap<String, Object> response = citaService.buscarPorPaciente(idPaciente);
        int code = (int) response.get("code");
        return new ResponseEntity<>(response, HttpStatusUtil.getStatus(code));
    }

    @PostMapping("")
    public ResponseEntity<?> crear(@RequestBody Cita cita) {
        // valida que el paciente exista
        if (cita.getPacienteCurp() == null || pacienteService.findByCurp(cita.getPacienteCurp()) == null) {
            return ResponseEntity.badRequest().body("Paciente no encontrado por CURP"); // error si no existe
        }
        System.out.println(cita); // imprime cita para depuración
        Cita saved = citaService.save(cita); // guarda cita
        return ResponseEntity.ok(saved); // devuelve cita guardada
    }

    @PutMapping("/atender-siguiente")
    public ResponseEntity<?> atender() {
        MyHashMap<String, Object> response = citaService.atenderSiguiente();
        int code = (int) response.get("code");
        HttpStatus status = HttpStatusUtil.getStatus(code);
        return new  ResponseEntity<>(response, status);
    }



}
