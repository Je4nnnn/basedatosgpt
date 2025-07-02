package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "citas_medicas")
public class CitaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de la cita no puede ser nula")
    @Future(message = "La fecha de la cita debe ser futura")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo de la cita no puede estar vacío")
    private String motivo;

    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidad;

    private String estado = "PENDIENTE"; // Campo para estado de la cita

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente es obligatorio")
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @NotNull(message = "El médico es obligatorio")
    private Usuario medico;
}
