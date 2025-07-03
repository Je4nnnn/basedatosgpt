package com.example.backend.service;

import com.example.backend.dto.MedicamentoStockDTO;
import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoService {

    /** Guarda o actualiza un medicamento */
    Medicamento registrarMedicamento(Medicamento m);

    /** Devuelve todos los medicamentos */
    List<Medicamento> listarMedicamentos();

    /** Crea un medicamento y su stock */
    StockMedicamento crearMedicamentoConStock(MedicamentoStockDTO dto);

    /** Busca un medicamento por nombre (usado en ExcelImportService) */
    Optional<Medicamento> buscarPorNombre(String nombreMedicamento);
}
