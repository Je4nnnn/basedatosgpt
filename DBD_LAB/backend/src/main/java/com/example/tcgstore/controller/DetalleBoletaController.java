package com.example.tcgstore.controller;

import com.example.tcgstore.model.DetalleBoleta;
import com.example.tcgstore.service.DetalleBoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-boletas")
public class DetalleBoletaController {

    @Autowired
    private DetalleBoletaService detalleBoletaService;

    @GetMapping
    public List<DetalleBoleta> listarDetalles() {
        return detalleBoletaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public DetalleBoleta obtenerPorId(@PathVariable Integer id) {
        return detalleBoletaService.obtenerPorId(id);
    }

    @PostMapping
    public DetalleBoleta crear(@RequestBody DetalleBoleta detalleBoleta) {
        return detalleBoletaService.guardar(detalleBoleta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        detalleBoletaService.eliminar(id);
    }
}
