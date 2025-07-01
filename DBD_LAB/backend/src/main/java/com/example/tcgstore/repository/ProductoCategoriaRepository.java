package com.example.tcgstore.repository;

import com.example.tcgstore.model.ProductoCategoria;
import com.example.tcgstore.model.ProductoCategoriaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, ProductoCategoriaId> {
}
