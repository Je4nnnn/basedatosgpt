package com.example.backend.service;

import com.example.backend.model.RecetaMedica;
import com.example.backend.dto.RecetaMedicaDTO;

import java.util.List;

public interface RecetaMedicaService {

    /** Registro “crudo” sin DTO */
    RecetaMedica registrarReceta(RecetaMedica recetaMedica);

    /** Listar todas las recetas */
    List<RecetaMedica> listarRecetas();

    /** Registro de receta vía DTO y asociada a un médico */
    RecetaMedica registrarReceta(RecetaMedicaDTO recetaMedicaDTO, Long medicoId);

    /** Elimina una receta por su ID */
    boolean eliminarRecetaPorId(Long id);

    /** Listar recetas de un médico */
    List<RecetaMedica> listarRecetasPorMedico(Long medicoId);

    /** Listar recetas pendientes para inventario */
    List<RecetaMedica> listarRecetasPorInventario();
}
