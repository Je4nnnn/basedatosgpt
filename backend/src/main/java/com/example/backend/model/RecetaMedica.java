package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "recetas_medicas")
public class RecetaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente es obligatorio")
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @NotNull(message = "El médico es obligatorio")
    private Usuario medico;
}
