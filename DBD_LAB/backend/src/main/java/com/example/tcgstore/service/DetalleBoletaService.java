package com.example.tcgstore.service;

import com.example.tcgstore.model.DetalleBoleta;
import com.example.tcgstore.repository.DetalleBoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleBoletaService {

    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    public List<DetalleBoleta> obtenerTodos() {
        return detalleBoletaRepository.findAll();
    }

    public DetalleBoleta obtenerPorId(Integer id) {
        return detalleBoletaRepository.findById(id).orElse(null);
    }

    public DetalleBoleta guardar(DetalleBoleta detalleBoleta) {
        return detalleBoletaRepository.save(detalleBoleta);
    }

    public void eliminar(Integer id) {
        detalleBoletaRepository.deleteById(id);
    }
}
