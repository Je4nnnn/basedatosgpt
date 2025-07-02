// backend/src/main/java/com/example/backend/service/DisponibilidadMedicoService.java
package com.example.backend.service;

import com.example.backend.model.DisponibilidadMedico;

import java.util.List;
import java.util.Optional;

public interface DisponibilidadMedicoService {

    /** Guarda una nueva franja disponible (o la re‐genera). */
    DisponibilidadMedico registrarDisponibilidad(DisponibilidadMedico disponibilidad);

    /** Lista solo las franjas con disponible=true. */
    List<DisponibilidadMedico> listarDisponibilidadesPorMedico(Long medicoId);

    /** Consulta una franja por su ID. */
    Optional<DisponibilidadMedico> obtenerDisponibilidadPorId(Long id);

    /** Marca la franja como NO disponible (cuando se agenda). */
    void marcarNoDisponible(Long id);

    /** Vuelve a marcar una franja existente como disponible. */
    void crearDisponibilidad(DisponibilidadMedico disponibilidad);
}
