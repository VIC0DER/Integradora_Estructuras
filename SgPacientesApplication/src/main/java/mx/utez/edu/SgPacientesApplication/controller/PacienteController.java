package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Paciente> listar() { return service.findAll(); }

    @GetMapping("/{curp}")
    public ResponseEntity<Paciente> buscarPorCurp(@PathVariable String curp) {
        Paciente p = service.findByCurp(curp);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        if (paciente.getCurp() == null || paciente.getCurp().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Paciente saved = service.save(paciente);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = service.deleteById(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{curp}/historial")
    public List<HistorialEntry> historial(@PathVariable String curp) {
        return service.getHistorial(curp);
    }

    @PostMapping("/historial/pop")
    public ResponseEntity<HistorialEntry> popHistorial() {
        HistorialEntry e = service.popUltimoHistorialDemo();
        return e == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(e);
    }
}
