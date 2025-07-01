package com.example.tcgstore.service;

import com.example.tcgstore.model.Boleta;
import com.example.tcgstore.repository.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    public List<Boleta> obtenerTodas() {
        return boletaRepository.findAll();
    }

    public Boleta obtenerPorId(Integer id) {
        return boletaRepository.findById(id).orElse(null);
    }

    public Boleta guardar(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    public void eliminar(Integer id) {
        boletaRepository.deleteById(id);
    }
}
