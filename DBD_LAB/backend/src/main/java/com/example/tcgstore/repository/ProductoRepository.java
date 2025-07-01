package com.example.tcgstore.repository;

import com.example.tcgstore.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Ejemplo de consulta para filtrar por ubicación de la tienda
    List<Producto> findByTienda_Jefe_Ubicacion(String ubicacion);
}
