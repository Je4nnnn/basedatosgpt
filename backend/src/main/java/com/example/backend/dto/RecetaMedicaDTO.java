package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaMedicaDTO {
    /** Nombre del medicamento a recetar */
    private String nombreMedicamento;

    /** Descripción de la receta */
    private String descripcion;

    /** ID del paciente */
    private Long pacienteId;
}
