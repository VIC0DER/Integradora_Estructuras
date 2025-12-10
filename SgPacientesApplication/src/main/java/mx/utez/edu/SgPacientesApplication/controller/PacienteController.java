package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    private final PacienteService service; // servicio de pacientes

    public PacienteController(PacienteService service) {
        this.service = service; // asigna servicio
    }


    @GetMapping("")
    public ResponseEntity<Object> listar() { return new ResponseEntity<>(service.findAll(), HttpStatus.OK); } // lista pacientes


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
}
