package com.example.tcgstore.controller;

import com.example.tcgstore.model.Valoracion;
import com.example.tcgstore.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @GetMapping
    public List<Valoracion> listarValoraciones() {
        return valoracionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Valoracion obtenerPorId(@PathVariable Integer id) {
        return valoracionService.obtenerPorId(id);
    }

    @PostMapping
    public Valoracion crear(@RequestBody Valoracion valoracion) {
        return valoracionService.guardar(valoracion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        valoracionService.eliminar(id);
    }
}
