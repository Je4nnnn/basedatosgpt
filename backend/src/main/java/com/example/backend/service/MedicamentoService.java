// src/main/java/com/example/backend/service/MedicamentoService.java
package com.example.backend.service;

import com.example.backend.dto.MedicamentoStockDTO;
import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;

import java.util.List;

public interface MedicamentoService {
    Medicamento registrarMedicamento(Medicamento m);
    List<Medicamento> listarMedicamentos();
    // 🔵 NUEVO:
    StockMedicamento crearMedicamentoConStock(MedicamentoStockDTO dto);
}