package com.example.backend.service.impl;

import com.example.backend.dto.MedicamentoStockDTO;
import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;
import com.example.backend.repository.MedicamentoRepository;
import com.example.backend.repository.StockMedicamentoRepository;
import com.example.backend.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private StockMedicamentoRepository stockRepository;

    @Override
    public Medicamento registrarMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    @Override
    public StockMedicamento crearMedicamentoConStock(MedicamentoStockDTO dto) {
        Medicamento med = new Medicamento();
        med.setNombre(dto.getNombre());
        med.setDescripcion(dto.getDescripcion());
        medicamentoRepository.save(med);

        StockMedicamento stock = new StockMedicamento();
        stock.setMedicamento(med);
        stock.setCantidadDisponible(dto.getCantidadDisponible());
        return stockRepository.save(stock);
    }

    @Override
    public Optional<Medicamento> buscarPorNombre(String nombreMedicamento) {
        return medicamentoRepository.findByNombre(nombreMedicamento);
    }
}
