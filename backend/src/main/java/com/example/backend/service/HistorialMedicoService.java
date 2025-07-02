package com.example.backend.service;

import com.example.backend.model.HistorialMedico;
import com.example.backend.dto.HistorialMedicoDTO;

import java.util.List;

public interface HistorialMedicoService {

    List<HistorialMedico> verHistorialPaciente(Long pacienteId);

    // 🔵 NUEVO: Método para registrar un historial
    HistorialMedico registrarHistorial(HistorialMedicoDTO historialMedicoDTO);
}
