package com.example.tcgstore.controller;

import com.example.tcgstore.model.CategoriaCarta;
import com.example.tcgstore.service.CategoriaCartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-carta")
public class CategoriaCartaController {

    @Autowired
    private CategoriaCartaService categoriaCartaService;

    @GetMapping
    public List<CategoriaCarta> listarTodas() {
        return categoriaCartaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public CategoriaCarta obtenerPorId(@PathVariable Integer id) {
        return categoriaCartaService.obtenerPorId(id);
    }

    @PostMapping
    public CategoriaCarta crear(@RequestBody CategoriaCarta categoriaCarta) {
        return categoriaCartaService.guardar(categoriaCarta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        categoriaCartaService.eliminar(id);
    }
}
