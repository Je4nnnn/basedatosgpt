// src/main/java/com/example/backend/dto/MedicamentoStockDTO.java
package com.example.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedicamentoStockDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidadDisponible;
}
