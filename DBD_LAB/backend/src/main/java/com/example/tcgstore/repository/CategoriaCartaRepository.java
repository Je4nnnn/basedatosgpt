package com.example.tcgstore.repository;

import com.example.tcgstore.model.CategoriaCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaCartaRepository extends JpaRepository<CategoriaCarta, Integer> {
}
