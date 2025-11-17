package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.Paciente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteRepository {

    private final List<Paciente> pacientes = new ArrayList<>();

    public Paciente save(Paciente p) {
        pacientes.add(p);
        return p;
    }

    public Paciente findByCurp(String curp) {
        for (Paciente p : pacientes) {
            if (p.getCurp().equalsIgnoreCase(curp)) {
                return p;
            }
        }
        return null;
    }

    public List<Paciente> findAll() {
        return pacientes;
    }

    public void deleteByCurp(String curp) {
        pacientes.removeIf(p -> p.getCurp().equalsIgnoreCase(curp));
    }
}
