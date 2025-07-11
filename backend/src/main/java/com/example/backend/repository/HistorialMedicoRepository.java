package com.example.backend.repository;

import com.example.backend.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

}
