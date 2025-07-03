package com.example.backend.controller;

import com.example.backend.dto.CitaMedicaResponseDTO;
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
import com.example.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medico")
//@CrossOrigin(origins = "*")
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
    private UsuarioService usuarioService;

    /** Devuelve sólo las citas PENDIENTES asignadas al médico */
    @GetMapping("/citas")
    public ResponseEntity<?> verCitasAsignadas(@RequestParam String email) {
        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty() || !"MEDICO".equals(usuarioOpt.get().getRol())) {
            return ResponseEntity
                    .status(403)
                    .body("Acceso denegado: Solo MÉDICOS pueden ver sus citas.");
        }

        Long medicoId = usuarioOpt.get().getId();
        List<CitaMedica> entidades =
                citaMedicaService.listarCitasPendientesPorMedico(medicoId);

        List<CitaMedicaResponseDTO> dto = entidades.stream().map(c -> {
            CitaMedicaResponseDTO r = new CitaMedicaResponseDTO();
            r.setId(c.getId());
            r.setFechaHora(c.getFechaHora());
            r.setMotivo(c.getMotivo());
            r.setEspecialidad(c.getEspecialidad());
            r.setEstado(c.getEstado());
            r.setPacienteId(c.getPaciente().getId());
            r.setPacienteNombre(c.getPaciente().getNombre());
            return r;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    /** Permite al médico registrar una nueva receta */
    @PostMapping("/recetas")
    public ResponseEntity<?> registrarReceta(
            @RequestParam String email,
            @RequestBody RecetaMedicaDTO recetaMedicaDTO) {

        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty() || !"MEDICO".equals(usuarioOpt.get().getRol())) {
            return ResponseEntity
                    .status(403)
                    .body("Acceso denegado: Solo MÉDICOS pueden registrar recetas.");
        }

        Usuario medico = usuarioOpt.get();
        RecetaMedica nuevaReceta =
                recetaMedicaService.registrarReceta(recetaMedicaDTO, medico.getId());

        return ResponseEntity.ok(nuevaReceta);
    }

    /** Permite al médico agregar una entrada al historial médico */
    @PostMapping("/historial")
    public ResponseEntity<?> registrarHistorial(
            @RequestBody RegistrarHistorialDTO registrarHistorialDTO) {

        Optional<Usuario> usuarioOpt =
                authService.buscarUsuarioPorEmail(registrarHistorialDTO.getEmailMedico());
        if (usuarioOpt.isEmpty() || !"MEDICO".equals(usuarioOpt.get().getRol())) {
            return ResponseEntity
                    .status(403)
                    .body("Acceso denegado: Solo MÉDICOS pueden registrar historial médico.");
        }

        HistorialMedico historialGuardado =
                historialMedicoService.registrarHistorial(
                        new HistorialMedicoDTO(
                                registrarHistorialDTO.getDiagnostico(),
                                registrarHistorialDTO.getPacienteId()
                        )
                );

        return ResponseEntity.ok(historialGuardado);
    }

    /** Finaliza una cita asignada al médico */
    @PutMapping("/finalizar-cita")
    public ResponseEntity<?> finalizarCita(
            @RequestParam String email,
            @RequestParam Long citaId) {

        Optional<Usuario> usuarioOpt = authService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty() || !"MEDICO".equals(usuarioOpt.get().getRol())) {
            return ResponseEntity
                    .status(403)
                    .body("Acceso denegado: Solo MÉDICOS pueden finalizar citas.");
        }

        Usuario medico = usuarioOpt.get();
        boolean finalizada =
                citaMedicaService.finalizarCita(citaId, medico.getId());

        if (finalizada) {
            return ResponseEntity.ok("Consulta finalizada exitosamente.");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("No se pudo finalizar la cita.");
        }
    }
}
