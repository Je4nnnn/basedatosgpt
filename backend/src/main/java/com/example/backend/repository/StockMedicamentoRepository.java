package com.example.backend.repository;

import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMedicamentoRepository extends JpaRepository<StockMedicamento, Long> {
    Optional<StockMedicamento> findByMedicamento(Medicamento medicamento);
}
