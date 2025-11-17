package mx.utez.edu.SgPacientesApplication.repository;

import mx.utez.edu.SgPacientesApplication.model.HistorialEntry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class HistorialRepository {

    private final List<HistorialEntry> historial = new ArrayList<>();

    public void save(HistorialEntry entry) {
        historial.add(entry);
    }

    // Obtener historial por CURP ordenado del más reciente al más antiguo
    public List<HistorialEntry> findByPacienteCurpOrderByFechaDesc(String curp) {
        List<HistorialEntry> lista = new ArrayList<>();

        for (HistorialEntry h : historial) {
            if (h.getPacienteCurp().equalsIgnoreCase(curp)) {
                lista.add(h);
            }
        }

        lista.sort(Comparator.comparing(HistorialEntry::getFecha).reversed());

        return lista;
    }

    public List<HistorialEntry> findAll() {
        return historial;
    }
}
