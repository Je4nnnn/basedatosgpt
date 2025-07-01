package com.example.tcgstore.repository;

import com.example.tcgstore.model.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {
}

