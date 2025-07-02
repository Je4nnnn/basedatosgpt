package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "disponibilidades_medico")
public class DisponibilidadMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraInicio; // NUEVO

    private LocalDateTime fechaHoraFin; // NUEVO

    private boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Usuario medico;

    public void setFechaInicio(LocalDateTime fechaHora) {

    }

    public void setFechaFin(LocalDateTime localDateTime) {

    }
}

