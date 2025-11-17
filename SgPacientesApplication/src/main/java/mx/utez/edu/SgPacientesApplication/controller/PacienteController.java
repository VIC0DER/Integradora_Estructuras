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
    private final PacienteService service; // servicio de pacientes

    public PacienteController(PacienteService service) {
        this.service = service; // asigna servicio
    }


    @GetMapping("")
    public List<Paciente> listar() { return service.findAll(); } // lista pacientes


    @GetMapping("/{curp}")
    public ResponseEntity<Paciente> buscarPorCurp(@PathVariable String curp) {
        Paciente p = service.findByCurp(curp); // busca por CURP
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p); // respuesta según hallazgo
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        if (paciente.getCurp() == null || paciente.getCurp().isBlank()) {
            return ResponseEntity.badRequest().build(); // valida curp
        }
        System.out.println(paciente); // depuración
        Paciente saved = service.save(paciente); // guarda paciente
        return ResponseEntity.ok(saved); // devuelve guardado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = service.deleteById(id); // elimina por id
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); // respuesta
    }

    @GetMapping("/{curp}/historial")
    public List<HistorialEntry> historial(@PathVariable String curp) {
        return service.getHistorial(curp); // devuelve historial del paciente
    }

    @PostMapping("/historial/pop")
    public ResponseEntity<HistorialEntry> popHistorial() {
        HistorialEntry e = service.popUltimoHistorialDemo(); // saca último historial global
        return e == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(e); // respuesta
    }
}
