package com.example.backend.dto;

/**
 * DTO para agendar cita:
 * - medicoId: id del médico
 * - fechaHoraInicio: ISO string, ej. "2025-05-02T10:00:00"
 * - motivo: motivo de la consulta
 * - especialidad: especialidad médica (Cardiología, Neurología, etc.)
 */
public class AgendarCitaRequest {

    private Long medicoId;
    private String fechaHoraInicio;
    private String motivo;
    private String especialidad; // 🔵 NUEVO: Campo para especialidad

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}