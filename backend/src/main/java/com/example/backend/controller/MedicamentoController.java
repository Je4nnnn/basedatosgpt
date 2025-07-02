package com.example.backend.controller;

import com.example.backend.dto.RegistrarMedicamentoDTO;
import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;
import com.example.backend.service.MedicamentoService;
import com.example.backend.service.StockMedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired private MedicamentoService medicamentoService;
    @Autowired private StockMedicamentoService stockService;

    /** 1) Crear sólo Medicamento **/
    @PostMapping
    public ResponseEntity<Medicamento> crearMedicamento(@Valid @RequestBody Medicamento med) {
        Medicamento guardado = medicamentoService.registrarMedicamento(med);
        return ResponseEntity.ok(guardado);
    }

    /** 2) Crear Medicamento + Stock en una sola llamada **/
    @Transactional
    @PostMapping("/con-stock")
    public ResponseEntity<Map<String, Object>> crearConStock(
            @Valid @RequestBody RegistrarMedicamentoDTO dto
    ) {
        // 2.1) creo el medicamento
        Medicamento med = new Medicamento();
        med.setNombre(dto.getNombre());
        med.setDescripcion(dto.getDescripcion());
        med = medicamentoService.registrarMedicamento(med);

        // 2.2) creo / actualizo stock
        StockMedicamento stk = new StockMedicamento();
        stk.setMedicamento(med);
        stk.setCantidadDisponible(dto.getCantidadDisponible());
        stk = stockService.actualizarStock(stk);

        // 2.3) devuelvo ambos en un Map
        return ResponseEntity.ok(Map.of(
                "medicamento", med,
                "stock", stk
        ));
    }

    /** 3) Listar todo **/
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(medicamentoService.listarMedicamentos());
    }
}
