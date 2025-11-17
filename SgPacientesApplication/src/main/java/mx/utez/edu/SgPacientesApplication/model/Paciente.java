package mx.utez.edu.SgPacientesApplication.model;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellidos;
    private String sexo;
    private String nacimiento; // ISO date e.g. 1990-01-01
    private String curp;
    private String nss;
    private String telefono;
    private String tipoSangre;
    private String medicamentos;
    private boolean alergias;

    public Paciente() {}

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getNacimiento() { return nacimiento; }
    public void setNacimiento(String nacimiento) { this.nacimiento = nacimiento; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getNss() { return nss; }
    public void setNss(String nss) { this.nss = nss; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }

    public boolean isAlergias() { return alergias; }
    public void setAlergias(boolean alergias) { this.alergias = alergias; }
}
