package com.example.tcgstore.service;

import com.example.tcgstore.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<BoletaService.Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    public BoletaService.Carrito obtenerPorId(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public BoletaService.Carrito guardar(BoletaService.Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void eliminar(Integer id) {
        carritoRepository.deleteById(id);
    }
}
