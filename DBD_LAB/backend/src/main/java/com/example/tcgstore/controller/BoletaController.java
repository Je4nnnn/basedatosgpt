package com.example.tcgstore.controller;

import com.example.tcgstore.model.Boleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping
    public List<Boleta> listarBoletas() {
        return boletaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Boleta obtenerPorId(@PathVariable Integer id) {
        return boletaService.obtenerPorId(id);
    }

    @PostMapping
    public Boleta crear(@RequestBody Boleta boleta) {
        return boletaService.guardar(boleta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        boletaService.eliminar(id);
    }
}
