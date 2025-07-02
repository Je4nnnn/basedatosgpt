package com.example.backend.controller;

import com.example.backend.model.CitaMedica;
import com.example.backend.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService citaMedicaService;

    // Agendar una nueva cita
    @PostMapping
    public ResponseEntity<?> agendarCita(@RequestBody CitaMedica citaMedica) {
        try {
            CitaMedica nuevaCita = citaMedicaService.agendarCita(citaMedica);
            return ResponseEntity.ok(nuevaCita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Listar todas las citas
    @GetMapping
    public ResponseEntity<List<CitaMedica>> listarCitas() {
        return ResponseEntity.ok(citaMedicaService.listarCitas());
    }

    // Listar citas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaMedica>> listarCitasPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaMedicaService.listarCitasPorPaciente(pacienteId));
    }

    // Listar citas PENDIENTES por médico (solo las no atendidas)
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaMedica>> listarCitasPendientesPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(citaMedicaService.listarCitasPendientesPorMedico(medicoId));
    }

    // Marcar cita como atendida/finalizada
    @PutMapping("/{citaId}/finalizar")
    public ResponseEntity<?> finalizarCita(@PathVariable Long citaId, @RequestParam Long medicoId) {
        try {
            boolean resultado = citaMedicaService.finalizarCita(citaId, medicoId);
            if (resultado) {
                return ResponseEntity.ok("Cita finalizada exitosamente");
            } else {
                return ResponseEntity.badRequest().body("No se pudo finalizar la cita");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Cancelar cita
    @PutMapping("/{citaId}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Long citaId, @RequestParam Long pacienteId) {
        try {
            boolean resultado = citaMedicaService.cancelarCita(citaId, pacienteId);
            if (resultado) {
                return ResponseEntity.ok("Cita cancelada exitosamente");
            } else {
                return ResponseEntity.badRequest().body("No se pudo cancelar la cita");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
