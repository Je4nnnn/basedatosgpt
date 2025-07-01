package com.example.tcgstore.service;

import com.example.tcgstore.model.Valoracion;
import com.example.tcgstore.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    public List<Valoracion> obtenerTodas() {
        return valoracionRepository.findAll();
    }

    public Valoracion obtenerPorId(Integer id) {
        return valoracionRepository.findById(id).orElse(null);
    }

    public Valoracion guardar(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    public void eliminar(Integer id) {
        valoracionRepository.deleteById(id);
    }
}
