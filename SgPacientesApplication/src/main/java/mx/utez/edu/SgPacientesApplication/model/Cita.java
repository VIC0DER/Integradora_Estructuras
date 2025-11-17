package mx.utez.edu.SgPacientesApplication.model;

public class Cita {
    private Long id;
    private String pacienteCurp;
    private String departamento;
    private String fechaHora; // ISO datetime e.g. 2025-11-20T10:30
    private String motivo;
    private String estado; // SOLICITADA, ATENDIDA, CANCELADA

    public Cita() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPacienteCurp() { return pacienteCurp; }
    public void setPacienteCurp(String pacienteCurp) { this.pacienteCurp = pacienteCurp; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
