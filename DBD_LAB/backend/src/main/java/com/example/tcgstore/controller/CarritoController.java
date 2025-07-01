package com.example.tcgstore.controller;

import com.example.tcgstore.model.Carrito;
import com.example.tcgstore.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Carrito obtenerPorId(@PathVariable Integer id) {
        return carritoService.obtenerPorId(id);
    }

    @PostMapping
    public Carrito crear(@RequestBody Carrito carrito) {
        return carritoService.guardar(carrito);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        carritoService.eliminar(id);
    }
}
