package com.example.tcgstore.service;

import com.example.tcgstore.model.ProductoCategoria;
import com.example.tcgstore.model.ProductoCategoriaId;
import com.example.tcgstore.repository.ProductoCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoCategoriaService {

    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;

    public List<ProductoCategoria> obtenerTodos() {
        return productoCategoriaRepository.findAll();
    }

    public ProductoCategoria guardar(ProductoCategoria productoCategoria) {
        return productoCategoriaRepository.save(productoCategoria);
    }

    public void eliminar(ProductoCategoriaId id) {
        productoCategoriaRepository.deleteById(id);
    }
}
