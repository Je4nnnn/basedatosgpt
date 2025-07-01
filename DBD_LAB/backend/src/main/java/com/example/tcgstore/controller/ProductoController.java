package com.example.tcgstore.controller;

import com.example.tcgstore.model.Producto;
import com.example.tcgstore.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
    }

    @GetMapping("/ubicacion/{ciudad}")
    public List<Producto> productosPorUbicacion(@PathVariable String ciudad) {
        return productoService.productosPorUbicacion(ciudad);
    }
}
