package mx.utez.edu.SgPacientesApplication.model;

import java.time.LocalDateTime;

public class HistorialEntry {
    private String pacienteCurp;
    private LocalDateTime fecha;
    private String descripcion;

    public HistorialEntry() {}

    public HistorialEntry(String pacienteCurp, LocalDateTime fecha, String descripcion) {
        this.pacienteCurp = pacienteCurp;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public String getPacienteCurp() { return pacienteCurp; }
    public void setPacienteCurp(String pacienteCurp) { this.pacienteCurp = pacienteCurp; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
