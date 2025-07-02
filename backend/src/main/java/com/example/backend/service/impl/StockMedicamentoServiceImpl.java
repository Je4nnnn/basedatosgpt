package com.example.backend.service.impl;

import com.example.backend.model.StockMedicamento;
import com.example.backend.repository.StockMedicamentoRepository;
import com.example.backend.service.StockMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockMedicamentoServiceImpl implements StockMedicamentoService {

    @Autowired
    private StockMedicamentoRepository stockRepo;

    @Override
    public StockMedicamento actualizarStock(StockMedicamento stock) {
        // Buscar si ya existe stock para ese medicamento
        Optional<StockMedicamento> existente = stockRepo.findByMedicamento(stock.getMedicamento());

        if (existente.isPresent()) {
            StockMedicamento actual = existente.get();
            actual.setCantidadDisponible(stock.getCantidadDisponible());
            return stockRepo.save(actual); // actualiza stock existente
        } else {
            return stockRepo.save(stock); // crea nuevo
        }
    }

    @Override
    public List<StockMedicamento> listarStock() {
        return stockRepo.findAll();
    }
}