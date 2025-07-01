package com.example.tcgstore.service;

import com.example.tcgstore.model.ListaDeseos;
import com.example.tcgstore.model.ListaDeseosId;
import com.example.tcgstore.repository.ListaDeseosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaDeseosService {

    @Autowired
    private ListaDeseosRepository listaDeseosRepository;

    public List<ListaDeseos> obtenerTodos() {
        return listaDeseosRepository.findAll();
    }

    public ListaDeseos guardar(ListaDeseos listaDeseos) {
        return listaDeseosRepository.save(listaDeseos);
    }

    public void eliminar(ListaDeseosId id) {
        listaDeseosRepository.deleteById(id);
    }
}
