package com.example.tcgstore.repository;

import com.example.tcgstore.model.CarritoProducto;
import com.example.tcgstore.model.CarritoProductoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, CarritoProductoId> {
}
