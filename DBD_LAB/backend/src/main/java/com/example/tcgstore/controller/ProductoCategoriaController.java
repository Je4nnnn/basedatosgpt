package com.example.tcgstore.controller;

import com.example.tcgstore.model.ProductoCategoria;
import com.example.tcgstore.model.ProductoCategoriaId;
import com.example.tcgstore.service.ProductoCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto-categoria")
public class ProductoCategoriaController {

    @Autowired
    private ProductoCategoriaService productoCategoriaService;

    @GetMapping
    public List<ProductoCategoria> listarTodos() {
        return productoCategoriaService.obtenerTodos();
    }

    @PostMapping
    public ProductoCategoria agregar(@RequestBody ProductoCategoria productoCategoria) {
        return productoCategoriaService.guardar(productoCategoria);
    }

    @DeleteMapping
    public void eliminar(@RequestBody ProductoCategoriaId id) {
        productoCategoriaService.eliminar(id);
    }
}
