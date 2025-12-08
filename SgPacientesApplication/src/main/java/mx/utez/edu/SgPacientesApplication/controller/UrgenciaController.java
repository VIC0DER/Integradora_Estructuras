package mx.utez.edu.SgPacientesApplication.controller;

import mx.utez.edu.SgPacientesApplication.dtos.CreateUrgenciaDTO;
import mx.utez.edu.SgPacientesApplication.service.UrgenciaService;
import mx.utez.edu.SgPacientesApplication.structures.MyHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urgencias")
@CrossOrigin(origins = "*")
public class UrgenciaController {
    private final UrgenciaService urgenciaService;
    public UrgenciaController(UrgenciaService urgenciaService) {
        this.urgenciaService = urgenciaService;
    }

    @GetMapping("")
    public ResponseEntity<Object> obtenerUrgencias() {
        return new ResponseEntity<>(urgenciaService.findCurrentUrgencies(), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<Object> insert(@RequestBody CreateUrgenciaDTO urgencia) {
        if(urgencia.getPrioridad() == 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MyHashMap<String, Object> response = urgenciaService.save(urgencia);
        int code = (int) response.get("code");
        return new ResponseEntity<>(response, getStatus(code));
    }

    @PatchMapping("/{id}/atendida")
    public ResponseEntity<Object> attend(@PathVariable Long id) {
        MyHashMap<String, Object> response = urgenciaService.attend(id);
        int code = (int) response.get("code");
        return new ResponseEntity<>(response, getStatus(code));
    }

    private HttpStatus getStatus(int code) {
        return switch (code) {
            case 200 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 404 -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
