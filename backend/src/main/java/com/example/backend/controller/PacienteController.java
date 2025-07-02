package com.example.backend.controller;

import com.example.backend.dto.AgendarCitaRequest;
import com.example.backend.model.CitaMedica;
import com.example.backend.model.DisponibilidadMedico;
import com.example.backend.model.Usuario;
import com.example.backend.service.AuthService;
import com.example.backend.service.CitaMedicaService;
import com.example.backend.service.DisponibilidadMedicoService;
import com.example.backend.service.HistorialMedicoService;
import com.example.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    @Autowired private CitaMedicaService citaMedicaService;
    @Autowired private HistorialMedicoService historialMedicoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private AuthService authService;
    @Autowired private DisponibilidadMedicoService disponibilidadMedicoService;

    private final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // 1) Agendar cita (query + path) - CORREGIDO TAMBIÉN
    @PostMapping("/agendar/{medicoId}")
    public ResponseEntity<?> agendarCitaPorQuery(
            @PathVariable Long medicoId,
            @RequestParam("nombre") String email,
            @RequestParam("fechaHoraInicio") String fechaHoraInicio,
            @RequestParam("motivo") String motivo,
            @RequestParam("especialidad") String especialidad // 🔥 AGREGADO
    ) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"PACIENTE".equals(u.get().getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado: Solo PACIENTES pueden agendar citas.");
        }

        Usuario medico = usuarioService.buscarUsuarioPorId(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(fechaHoraInicio, ISO);
        } catch (DateTimeException ex) {
            return ResponseEntity.badRequest()
                    .body("Formato de fecha inválido. Use ISO_LOCAL_DATE_TIME");
        }

        CitaMedica cita = new CitaMedica();
        cita.setPaciente(u.get());
        cita.setMedico(medico);
        cita.setFechaHora(dt);
        cita.setMotivo(motivo);
        cita.setEspecialidad(especialidad); // 🔥 AGREGADO
        cita.setEstado("AGENDADA");

        CitaMedica guardada = citaMedicaService.agendarCita(cita);
        return ResponseEntity.ok(guardada);
    }

    // 2) Agendar cita (JSON + email) - CORREGIDO
    @PostMapping("/citas")
    public ResponseEntity<?> agendarCitaPorBody(
            @RequestParam("email") String email,
            @RequestBody AgendarCitaRequest req
    ) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"PACIENTE".equals(u.get().getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado: Solo PACIENTES pueden agendar citas.");
        }

        Usuario medico = usuarioService.buscarUsuarioPorId(req.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(req.getFechaHoraInicio(), ISO);
        } catch (DateTimeException ex) {
            return ResponseEntity.badRequest()
                    .body("Formato de fecha inválido. Use ISO_LOCAL_DATE_TIME");
        }

        CitaMedica cita = new CitaMedica();
        cita.setPaciente(u.get());
        cita.setMedico(medico);
        cita.setFechaHora(dt);
        cita.setMotivo(req.getMotivo());
        cita.setEspecialidad(req.getEspecialidad()); // 🔥 LÍNEA AGREGADA - ESTA ERA LA QUE FALTABA
        cita.setEstado("AGENDADA");

        CitaMedica guardada = citaMedicaService.agendarCita(cita);
        return ResponseEntity.ok(guardada);
    }

    // 3) Ver citas del paciente
    @GetMapping("/citas")
    public ResponseEntity<?> verCitasPaciente(@RequestParam String email) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"PACIENTE".equals(u.get().getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado: Solo PACIENTES pueden ver sus citas.");
        }
        List<CitaMedica> citas = citaMedicaService.listarCitasPorPaciente(u.get().getId());
        return ResponseEntity.ok(citas);
    }

    // 4) Cancelar cita y liberar disponibilidad
    @DeleteMapping("/citas/{idCita}")
    public ResponseEntity<?> cancelarCita(
            @RequestParam String email,
            @PathVariable Long idCita
    ) {
        // Validar paciente
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"PACIENTE".equals(u.get().getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado: Solo PACIENTES pueden cancelar citas.");
        }

        // Recuperar cita antes de borrarla
        Optional<CitaMedica> opt = citaMedicaService.buscarPorId(idCita);
        if (opt.isEmpty() || !opt.get().getPaciente().getId().equals(u.get().getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cita no encontrada o no pertenece a este paciente.");
        }
        CitaMedica cita = opt.get();

        // Borrar cita
        citaMedicaService.cancelarCita(idCita, u.get().getId());

        // Volver a crear franja EXACTA usando los setters correctos
        DisponibilidadMedico disp = new DisponibilidadMedico();
        disp.setMedico(cita.getMedico());
        // ⚠️ AQUÍ: usa los nombres de campo de tu entidad
        disp.setFechaHoraInicio(cita.getFechaHora());
        disp.setFechaHoraFin(cita.getFechaHora().plusMinutes(30)); // o la duración real
        disponibilidadMedicoService.crearDisponibilidad(disp);

        return ResponseEntity.ok("Cita cancelada y disponibilidad liberada correctamente.");
    }

    // 5) Ver historial médico
    @GetMapping("/historial")
    public ResponseEntity<?> verHistorialMedico(@RequestParam String email) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"PACIENTE".equals(u.get().getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado: Solo PACIENTES pueden ver su historial.");
        }
        return ResponseEntity.ok(
                historialMedicoService.verHistorialPaciente(u.get().getId())
        );
    }

    // 6) Ver disponibilidades
    @GetMapping("/disponibilidad")
    public ResponseEntity<?> verDisponibilidades(@RequestParam Long medicoId) {
        return ResponseEntity.ok(
                disponibilidadMedicoService.listarDisponibilidadesPorMedico(medicoId)
        );
    }

    @GetMapping("/medicos/especialidad/{especialidad}")
    public ResponseEntity<?> obtenerMedicosPorEspecialidad(@PathVariable String especialidad) {
        try {
            // 🔥 CORREGIDO: Filtrar médicos por especialidad Y disponibilidad
            List<Usuario> medicosEspecialidad = usuarioService.listarUsuarios()
                    .stream()
                    .filter(u -> "MEDICO".equals(u.getRol()))
                    .filter(medico -> {
                        // Filtrar por especialidad (asumiendo que tienes un campo especialidad en Usuario)
                        // Ajusta este filtro según cómo tengas modelada la especialidad
                        return especialidad.equals(medico.getEspecialidad()) ||
                                especialidad.equalsIgnoreCase(medico.getEspecialidad());
                    })
                    .filter(medico -> {
                        // Verificar si el médico tiene disponibilidad
                        List<DisponibilidadMedico> disponibilidades =
                                disponibilidadMedicoService.listarDisponibilidadesPorMedico(medico.getId());
                        return !disponibilidades.isEmpty();
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(medicosEspecialidad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener médicos: " + e.getMessage());
        }
    }
}