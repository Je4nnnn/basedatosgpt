package com.example.tcgstore.service;

import com.example.tcgstore.model.Tienda;
import com.example.tcgstore.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> obtenerTodas() {
        return tiendaRepository.findAll();
    }

    public Tienda obtenerPorId(Integer id) {
        return tiendaRepository.findById(id).orElse(null);
    }

    public Tienda guardar(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public void eliminar(Integer id) {
        tiendaRepository.deleteById(id);
    }
}
