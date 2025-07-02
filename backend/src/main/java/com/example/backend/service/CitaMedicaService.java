package com.example.backend.service;

import com.example.backend.model.CitaMedica;
import java.util.List;
import java.util.Optional;

public interface CitaMedicaService {

    CitaMedica agendarCita(CitaMedica citaMedica);

    List<CitaMedica> listarCitas();

    List<CitaMedica> listarCitasPorPaciente(Long pacienteId);

    List<CitaMedica> listarCitasPendientesPorMedico(Long medicoId);

    boolean cancelarCita(Long idCita, Long pacienteId);

    boolean finalizarCita(Long idCita, Long medicoId);

    Optional<CitaMedica> buscarPorId(Long idCita);
}