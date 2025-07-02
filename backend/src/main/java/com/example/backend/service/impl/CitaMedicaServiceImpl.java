package com.example.backend.service.impl;

import com.example.backend.model.CitaMedica;
import com.example.backend.model.Usuario;
import com.example.backend.repository.CitaMedicaRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.CitaMedicaService;
import com.example.backend.service.DisponibilidadMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    @Autowired
    private CitaMedicaRepository citaMedicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisponibilidadMedicoService disponibilidadMedicoService;

    // Configuración de límites
    @Value("${citas.limite.maximo:3}")
    private int limiteMaximoCitas;

    @Value("${citas.limite.mensual:5}")
    private int limiteMensualCitas;

    @Override
    public CitaMedica agendarCita(CitaMedica citaMedica) {
        // 🔵 NUEVO: Validar que la especialidad no esté vacía
        if (citaMedica.getEspecialidad() == null || citaMedica.getEspecialidad().trim().isEmpty()) {
            throw new RuntimeException("La especialidad es obligatoria");
        }

        // 🔵 NUEVO: Validar que el paciente no tenga ya una cita activa con la misma especialidad
        if (citaMedicaRepository.existsCitaActivaByPacienteAndEspecialidad(citaMedica.getPaciente(), citaMedica.getEspecialidad().trim())) {
            throw new RuntimeException("Ya tienes una cita activa para la especialidad: " + citaMedica.getEspecialidad());
        }

        // Validar restricciones existentes antes de agendar
        validarLimiteCitas(citaMedica);
        validarConflictoHorario(citaMedica);

        // Establecer estado por defecto
        if (citaMedica.getEstado() == null || citaMedica.getEstado().isEmpty()) {
            citaMedica.setEstado("PENDIENTE");
        }

        CitaMedica nuevaCita = citaMedicaRepository.save(citaMedica);

        // Marcar horario como no disponible
        disponibilidadMedicoService.listarDisponibilidadesPorMedico(citaMedica.getMedico().getId())
                .stream()
                .filter(d ->
                        d.isDisponible() &&
                                !citaMedica.getFechaHora().isBefore(d.getFechaHoraInicio()) &&
                                !citaMedica.getFechaHora().isAfter(d.getFechaHoraFin())
                )
                .forEach(d -> disponibilidadMedicoService.marcarNoDisponible(d.getId()));

        return nuevaCita;
    }

    @Override
    public List<CitaMedica> listarCitas() {
        return citaMedicaRepository.findAll();
    }

    @Override
    public List<CitaMedica> listarCitasPorPaciente(Long pacienteId) {
        return citaMedicaRepository.findCitasActivasByPacienteId(pacienteId);
    }

    @Override
    public List<CitaMedica> listarCitasPendientesPorMedico(Long medicoId) {
        // SOLO devolver citas PENDIENTES, no las finalizadas
        return citaMedicaRepository.findCitasPendientesByMedico(medicoId);
    }

    @Override
    public boolean cancelarCita(Long idCita, Long pacienteId) {
        Optional<CitaMedica> citaOptional = citaMedicaRepository.findById(idCita);
        if (citaOptional.isPresent() && citaOptional.get().getPaciente().getId().equals(pacienteId)) {
            CitaMedica cita = citaOptional.get();
            cita.setEstado("CANCELADA");
            citaMedicaRepository.save(cita);
            return true;
        }
        return false;
    }

    @Override
    public boolean finalizarCita(Long idCita, Long medicoId) {
        Optional<CitaMedica> citaOptional = citaMedicaRepository.findById(idCita);
        if (citaOptional.isPresent() && citaOptional.get().getMedico().getId().equals(medicoId)) {
            CitaMedica cita = citaOptional.get();
            cita.setEstado("FINALIZADA");
            citaMedicaRepository.save(cita);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CitaMedica> buscarPorId(Long idCita) {
        return citaMedicaRepository.findById(idCita);
    }

    // Métodos privados de validación
    private void validarLimiteCitas(CitaMedica citaMedica) {
        // Validar límite total de citas activas
        long citasActivas = citaMedicaRepository.countCitasActivasByPaciente(citaMedica.getPaciente());

        if (citasActivas >= limiteMaximoCitas) {
            throw new RuntimeException(
                    String.format("El paciente ya tiene %d citas activas. Límite máximo: %d",
                            citasActivas, limiteMaximoCitas)
            );
        }

        // Validar límite mensual
        LocalDateTime inicioMes = citaMedica.getFechaHora().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finMes = inicioMes.plusMonths(1).minusSeconds(1);

        long citasMensual = citaMedicaRepository.countCitasActivasByPacienteAndFechaRange(
                citaMedica.getPaciente(), inicioMes, finMes
        );

        if (citasMensual >= limiteMensualCitas) {
            throw new RuntimeException(
                    String.format("El paciente ya tiene %d citas este mes. Límite mensual: %d",
                            citasMensual, limiteMensualCitas)
            );
        }
    }

    private void validarConflictoHorario(CitaMedica citaMedica) {
        boolean existeConflicto = citaMedicaRepository.existsCitaConflictoByPacienteAndFecha(
                citaMedica.getPaciente(), citaMedica.getFechaHora()
        );

        if (existeConflicto) {
            throw new RuntimeException("El paciente ya tiene una cita programada en esa fecha y hora");
        }
    }
}