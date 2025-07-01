package com.example.tcgstore.service;

import com.example.tcgstore.model.CategoriaCarta;
import com.example.tcgstore.repository.CategoriaCartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaCartaService {

    @Autowired
    private CategoriaCartaRepository categoriaCartaRepository;

    public List<CategoriaCarta> obtenerTodas() {
        return categoriaCartaRepository.findAll();
    }

    public CategoriaCarta obtenerPorId(Integer id) {
        return categoriaCartaRepository.findById(id).orElse(null);
    }

    public CategoriaCarta guardar(CategoriaCarta categoriaCarta) {
        return categoriaCartaRepository.save(categoriaCarta);
    }

    public void eliminar(Integer id) {
        categoriaCartaRepository.deleteById(id);
    }
}
