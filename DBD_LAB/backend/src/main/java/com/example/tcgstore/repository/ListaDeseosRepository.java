package com.example.tcgstore.repository;

import com.example.tcgstore.model.ListaDeseos;
import com.example.tcgstore.model.ListaDeseosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, ListaDeseosId> {
}
