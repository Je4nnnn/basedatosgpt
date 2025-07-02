package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaMedicaDTO {
    private String nombreMedicamento;
    private String descripcion;
    private Long pacienteId;
}

