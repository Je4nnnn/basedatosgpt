package com.example.tcgstore.controller;

import com.example.tcgstore.model.Tienda;
import com.example.tcgstore.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public List<Tienda> listarTiendas() {
        return tiendaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Tienda obtenerPorId(@PathVariable Integer id) {
        return tiendaService.obtenerPorId(id);
    }

    @PostMapping
    public Tienda crear(@RequestBody Tienda tienda) {
        return tiendaService.guardar(tienda);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        tiendaService.eliminar(id);
    }
}
