package com.example.tcgstore.controller;

import com.example.tcgstore.model.Categoria;
import com.example.tcgstore.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable Integer id) {
        return categoriaService.obtenerPorId(id);
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
    }
}
