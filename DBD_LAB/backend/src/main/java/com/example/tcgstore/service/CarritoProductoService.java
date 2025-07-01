package com.example.tcgstore.service;

import com.example.tcgstore.model.CarritoProducto;
import com.example.tcgstore.model.CarritoProductoId;
import com.example.tcgstore.repository.CarritoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoProductoService {

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    public List<CarritoProducto> obtenerTodos() {
        return carritoProductoRepository.findAll();
    }

    public CarritoProducto guardar(CarritoProducto carritoProducto) {
        return carritoProductoRepository.save(carritoProducto);
    }

    public void eliminar(CarritoProductoId id) {
        carritoProductoRepository.deleteById(id);
    }
}
