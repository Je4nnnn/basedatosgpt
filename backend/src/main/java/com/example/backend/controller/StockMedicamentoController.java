package com.example.backend.controller;

import com.example.backend.model.StockMedicamento;
import com.example.backend.service.StockMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockMedicamentoController {

    @Autowired
    private StockMedicamentoService stockMedicamentoService;

    // Crear o actualizar stock
    @PostMapping
    public ResponseEntity<StockMedicamento> crearOActualizarStock(@RequestBody StockMedicamento stockMedicamento) {
        StockMedicamento nuevo = stockMedicamentoService.actualizarStock(stockMedicamento);
        return ResponseEntity.ok(nuevo);
    }

    // Listar stock
    @GetMapping
    public ResponseEntity<List<StockMedicamento>> listarStock() {
        return ResponseEntity.ok(stockMedicamentoService.listarStock());
    }
}
