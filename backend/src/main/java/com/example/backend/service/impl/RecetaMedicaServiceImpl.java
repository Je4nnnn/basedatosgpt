package com.example.backend.service.impl;

import com.example.backend.dto.RecetaMedicaDTO;
import com.example.backend.model.Medicamento;
import com.example.backend.model.RecetaMedica;
import com.example.backend.model.StockMedicamento;
import com.example.backend.model.Usuario;
import com.example.backend.repository.MedicamentoRepository;
import com.example.backend.repository.RecetaMedicaRepository;
import com.example.backend.repository.StockMedicamentoRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.RecetaMedicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecetaMedicaServiceImpl implements RecetaMedicaService {

    @Autowired private RecetaMedicaRepository recetaMedicaRepository;
    @Autowired private MedicamentoRepository medicamentoRepository;
    @Autowired private StockMedicamentoRepository stockMedicamentoRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public RecetaMedica registrarReceta(RecetaMedicaDTO dto, Long medicoId) {
        // 1) Paciente
        Usuario paciente = usuarioRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // 2) Medicamento: buscar o crear
        Medicamento medicamento = medicamentoRepository
                .findByNombre(dto.getNombreMedicamento())
                .orElseGet(() -> {
                    Medicamento m = new Medicamento();
                    m.setNombre(dto.getNombreMedicamento());
                    m.setDescripcion(dto.getDescripcion());
                    Medicamento guardado = medicamentoRepository.save(m);
                    // inicializar stock
                    StockMedicamento s = new StockMedicamento();
                    s.setMedicamento(guardado);
                    s.setCantidadDisponible(10);
                    stockMedicamentoRepository.save(s);
                    return guardado;
                });

        // 3) Descontar stock
        StockMedicamento stock = stockMedicamentoRepository
                .findByMedicamento(medicamento)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        if (stock.getCantidadDisponible() <= 0) {
            throw new RuntimeException("Sin stock de " + medicamento.getNombre());
        }
        stock.setCantidadDisponible(stock.getCantidadDisponible() - 1);
        stockMedicamentoRepository.save(stock);

        // 4) Crear y guardar receta
        RecetaMedica receta = new RecetaMedica();
        receta.setDescripcion(dto.getDescripcion());
        receta.setFechaEmision(LocalDate.now());
        receta.setPaciente(paciente);
        receta.setMedico(usuarioRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado")));

        return recetaMedicaRepository.save(receta);
    }

    @Override
    public RecetaMedica registrarReceta(RecetaMedica recetaMedica) {
        return recetaMedicaRepository.save(recetaMedica);
    }

    @Override
    public List<RecetaMedica> listarRecetas() {
        return recetaMedicaRepository.findAll();
    }

    @Override
    public boolean eliminarRecetaPorId(Long id) {
        if (recetaMedicaRepository.existsById(id)) {
            recetaMedicaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<RecetaMedica> listarRecetasPorMedico(Long medicoId) {
        return recetaMedicaRepository.findAll().stream()
                .filter(r -> r.getMedico() != null && r.getMedico().getId().equals(medicoId))
                .toList();
    }

    @Override
    public List<RecetaMedica> listarRecetasPorInventario() {
        return recetaMedicaRepository.findAll();
    }
}
