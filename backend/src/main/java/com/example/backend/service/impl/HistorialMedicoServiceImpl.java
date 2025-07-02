package com.example.backend.service.impl;

import com.example.backend.model.HistorialMedico;
import com.example.backend.model.Usuario;
import com.example.backend.repository.HistorialMedicoRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.HistorialMedicoService;
import com.example.backend.dto.HistorialMedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoServiceImpl implements HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<HistorialMedico> verHistorialPaciente(Long pacienteId) {
        return historialMedicoRepository.findAll().stream()
                .filter(historial -> historial.getPaciente().getId().equals(pacienteId))
                .toList();
    }

    @Override
    public HistorialMedico registrarHistorial(HistorialMedicoDTO historialMedicoDTO) {
        Usuario paciente = usuarioRepository.findById(historialMedicoDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        HistorialMedico historial = new HistorialMedico();
        historial.setDiagnostico(historialMedicoDTO.getDiagnostico());
        historial.setPaciente(paciente);

        return historialMedicoRepository.save(historial);
    }
}

