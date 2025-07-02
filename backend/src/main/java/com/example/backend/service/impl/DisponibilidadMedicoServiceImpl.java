// backend/src/main/java/com/example/backend/service/impl/DisponibilidadMedicoServiceImpl.java
package com.example.backend.service.impl;

import com.example.backend.model.DisponibilidadMedico;
import com.example.backend.repository.DisponibilidadMedicoRepository;
import com.example.backend.service.DisponibilidadMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadMedicoServiceImpl implements DisponibilidadMedicoService {

    @Autowired
    private DisponibilidadMedicoRepository disponibilidadMedicoRepository;

    @Override
    public DisponibilidadMedico registrarDisponibilidad(DisponibilidadMedico disponibilidad) {
        disponibilidad.setDisponible(true);
        return disponibilidadMedicoRepository.save(disponibilidad);
    }

    @Override
    public List<DisponibilidadMedico> listarDisponibilidadesPorMedico(Long medicoId) {
        return disponibilidadMedicoRepository.findByMedicoIdAndDisponibleTrue(medicoId);
    }

    @Override
    public Optional<DisponibilidadMedico> obtenerDisponibilidadPorId(Long id) {
        return disponibilidadMedicoRepository.findById(id);
    }

    @Override
    public void marcarNoDisponible(Long id) {
        disponibilidadMedicoRepository.findById(id).ifPresent(d -> {
            d.setDisponible(false);
            disponibilidadMedicoRepository.save(d);
        });
    }

    @Override
    public void crearDisponibilidad(DisponibilidadMedico disp) {
        // re-habilita la misma franja cancelada
        disp.setDisponible(true);
        disponibilidadMedicoRepository.save(disp);
    }
}
