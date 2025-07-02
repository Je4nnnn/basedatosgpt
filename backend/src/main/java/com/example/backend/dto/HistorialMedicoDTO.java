package com.example.backend.dto;

public class HistorialMedicoDTO {

    private String diagnostico;
    private Long pacienteId;

    // Constructor vacío (necesario para Jackson)
    public HistorialMedicoDTO() {}

    // Constructor que usamos en el controller
    public HistorialMedicoDTO(String diagnostico, Long pacienteId) {
        this.diagnostico = diagnostico;
        this.pacienteId = pacienteId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
