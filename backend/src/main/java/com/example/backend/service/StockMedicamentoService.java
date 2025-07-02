package com.example.backend.service;

import com.example.backend.model.StockMedicamento;
import java.util.List;

public interface StockMedicamentoService {
    StockMedicamento actualizarStock(StockMedicamento stock);
    List<StockMedicamento> listarStock();
}
