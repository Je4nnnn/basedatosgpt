package com.example.tcgstore.service;

import com.example.tcgstore.model.Carrito;
import com.example.tcgstore.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    public Carrito obtenerPorId(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    public Carrito guardar(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void eliminar(Integer id) {
        carritoRepository.deleteById(id);
    }
}
