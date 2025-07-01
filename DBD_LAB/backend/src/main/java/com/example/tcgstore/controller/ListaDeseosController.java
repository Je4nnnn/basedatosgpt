package com.example.tcgstore.controller;

import com.example.tcgstore.model.ListaDeseos;
import com.example.tcgstore.model.ListaDeseosId;
import com.example.tcgstore.service.ListaDeseosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-deseos")
public class ListaDeseosController {

    @Autowired
    private ListaDeseosService listaDeseosService;

    @GetMapping
    public List<ListaDeseos> listarDeseos() {
        return listaDeseosService.obtenerTodos();
    }

    @PostMapping
    public ListaDeseos agregar(@RequestBody ListaDeseos listaDeseos) {
        return listaDeseosService.guardar(listaDeseos);
    }

    @DeleteMapping
    public void eliminar(@RequestBody ListaDeseosId id) {
        listaDeseosService.eliminar(id);
    }
}

