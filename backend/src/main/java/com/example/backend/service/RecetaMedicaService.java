package com.example.backend.service;

import com.example.backend.model.RecetaMedica;
import com.example.backend.dto.RecetaMedicaDTO;

import java.util.List;

public interface RecetaMedicaService {

    // Registro “crudo”
    RecetaMedica registrarReceta(RecetaMedica recetaMedica);

    // Listar TODO (p.ej. para inventario)
    List<RecetaMedica> listarRecetas();

    // Registro vía DTO + stock
    RecetaMedica registrarReceta(RecetaMedicaDTO recetaMedicaDTO, Long medicoId);

    // Eliminar receta al confirmar entrega
    boolean eliminarRecetaPorId(Long id);

    // 🔵 MÉDICO ve SOLO sus propias recetas
    List<RecetaMedica> listarRecetasPorMedico(Long medicoId);

    // 🔵 INVENTARIO ve TODAS las recetas pendientes
    List<RecetaMedica> listarRecetasPorInventario();
}
