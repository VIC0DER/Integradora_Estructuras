package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.service.CitaService;
import mx.utez.edu.SgPacientesApplication.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;

    public CitaController(CitaService citaService, PacienteService pacienteService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Cita> listar() { return citaService.findAll(); }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cita cita) {
        // validar paciente existe
        if (cita.getPacienteCurp() == null || pacienteService.findByCurp(cita.getPacienteCurp()) == null) {
            return ResponseEntity.badRequest().body("Paciente no encontrado por CURP");
        }
        Cita saved = citaService.save(cita);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/atender-siguiente")
    public ResponseEntity<?> atender() {
        Cita atendida = citaService.atenderSiguiente();
        return atendida == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(atendida);
    }

    @GetMapping("/cola/size")
    public ResponseEntity<Integer> colaSize() {
        return ResponseEntity.ok(citaService.colaSize());
    }
}
