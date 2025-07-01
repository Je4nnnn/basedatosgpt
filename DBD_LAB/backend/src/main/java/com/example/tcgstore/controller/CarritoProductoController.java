package com.example.tcgstore.controller;

import com.example.tcgstore.model.CarritoProducto;
import com.example.tcgstore.model.CarritoProductoId;
import com.example.tcgstore.service.CarritoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito-producto")
public class CarritoProductoController {

    @Autowired
    private CarritoProductoService carritoProductoService;

    @GetMapping
    public List<CarritoProducto> listarTodos() {
        return carritoProductoService.obtenerTodos();
    }

    @PostMapping
    public CarritoProducto agregar(@RequestBody CarritoProducto carritoProducto) {
        return carritoProductoService.guardar(carritoProducto);
    }

    @DeleteMapping
    public void eliminar(@RequestBody CarritoProductoId id) {
        carritoProductoService.eliminar(id);
    }
}
