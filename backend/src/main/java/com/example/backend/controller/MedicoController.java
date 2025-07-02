package com.example.backend.controller;

import com.example.backend.dto.RecetaMedicaDTO;
import com.example.backend.dto.RegistrarHistorialDTO;
import com.example.backend.dto.HistorialMedicoDTO;
import com.example.backend.model.CitaMedica;
import com.example.backend.model.HistorialMedico;
import com.example.backend.model.RecetaMedica;
import com.example.backend.model.Usuario;
import com.example.backend.service.AuthService;
import com.example.backend.service.CitaMedicaService;
import com.example.backend.service.DisponibilidadMedicoService;
import com.example.backend.service.HistorialMedicoService;
import com.example.backend.service.RecetaMedicaService;
import com.example.backend.service.UsuarioService; // Necesario para buscar médicos
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medico")
@CrossOrigin(origins = "*") // Agregar CORS si es necesario
public class MedicoController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DisponibilidadMedicoService disponibilidadMedicoService;

    @Autowired
    private CitaMedicaService citaMedicaService;

    @Autowired
    private RecetaMedicaService recetaMedicaService;

    @Autowired
    private HistorialMedicoService historialMedicoService;

    @Autowired
    private UsuarioService usuarioService; // Para buscar médicos por especialidad

    @GetMapping("/citas")
    public ResponseEntity<?> verCitasAsignadas(@RequestParam String email) {
        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getRol().equals("MEDICO")) {
            return ResponseEntity.status(403).body("Acceso denegado: Solo MÉDICOS pueden ver sus citas.");
        }

        Usuario medico = usuarioOpt.get();
        // ✅ CAMBIO: Usar método que solo devuelve citas PENDIENTES
        List<CitaMedica> citasAsignadas = citaMedicaService.listarCitasPendientesPorMedico(medico.getId());

        return ResponseEntity.ok(citasAsignadas);
    }

    @PostMapping("/recetas")
    public ResponseEntity<?> registrarReceta(@RequestParam String email, @RequestBody RecetaMedicaDTO recetaMedicaDTO) {
        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getRol().equals("MEDICO")) {
            return ResponseEntity.status(403).body("Acceso denegado: Solo MÉDICOS pueden registrar recetas.");
        }

        Usuario medico = usuarioOpt.get();
        RecetaMedica nuevaReceta = recetaMedicaService.registrarReceta(recetaMedicaDTO, medico.getId());

        return ResponseEntity.ok(nuevaReceta);
    }

    @PostMapping("/historial")
    public ResponseEntity<?> registrarHistorial(@RequestBody RegistrarHistorialDTO registrarHistorialDTO) {
        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(registrarHistorialDTO.getEmailMedico());

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getRol().equals("MEDICO")) {
            return ResponseEntity.status(403).body("Acceso denegado: Solo MÉDICOS pueden registrar historial médico.");
        }

        HistorialMedico historialGuardado = historialMedicoService.registrarHistorial(
                new HistorialMedicoDTO(registrarHistorialDTO.getDiagnostico(), registrarHistorialDTO.getPacienteId())
        );

        return ResponseEntity.ok(historialGuardado);
    }

    // Finalizar una cita médica
    @PutMapping("/finalizar-cita")
    public ResponseEntity<?> finalizarCita(@RequestParam String email, @RequestParam Long citaId) {
        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getRol().equals("MEDICO")) {
            return ResponseEntity.status(403).body("Acceso denegado: Solo MÉDICOS pueden finalizar citas.");
        }

        Usuario medico = usuarioOpt.get();

        boolean finalizada = citaMedicaService.finalizarCita(citaId, medico.getId());

        if (finalizada) {
            return ResponseEntity.ok("Consulta finalizada exitosamente.");
        } else {
            return ResponseEntity.status(404).body("No se pudo finalizar la cita.");
        }
    }
}
