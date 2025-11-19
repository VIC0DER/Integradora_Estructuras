package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.service.CitaService;
import mx.utez.edu.SgPacientesApplication.service.PacienteService;
import mx.utez.edu.SgPacientesApplication.structures.ListaSimple;
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

    @GetMapping
    public ListaSimple<Cita> listar() { return citaService.findAll(); } // lista todas las citas

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cita cita) {
        // valida que el paciente exista
        if (cita.getPacienteCurp() == null || pacienteService.findByCurp(cita.getPacienteCurp()) == null) {
            return ResponseEntity.badRequest().body("Paciente no encontrado por CURP"); // error si no existe
        }
        System.out.println(cita); // imprime cita para depuración
        Cita saved = citaService.save(cita); // guarda cita
        return ResponseEntity.ok(saved); // devuelve cita guardada
    }

    @PostMapping("/atender-siguiente")
    public ResponseEntity<?> atender() {
        Cita atendida = citaService.atenderSiguiente(); // atiende siguiente cita
        return atendida == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(atendida); // respuesta según resultado
    }

    @GetMapping("/cola/size")
    public ResponseEntity<Integer> colaSize() {
        return ResponseEntity.ok(citaService.colaSize()); // devuelve tamaño de la cola
    }
}
