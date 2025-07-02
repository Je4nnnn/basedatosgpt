package com.example.backend.repository;

import com.example.backend.model.CitaMedica;
import com.example.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    // 🔵 NUEVO: Verificar si el paciente ya tiene una cita activa con la misma especialidad
    @Query("SELECT COUNT(c) > 0 FROM CitaMedica c WHERE c.paciente = :paciente AND c.especialidad = :especialidad AND c.estado NOT IN ('FINALIZADA', 'CANCELADA')")
    boolean existsCitaActivaByPacienteAndEspecialidad(@Param("paciente") Usuario paciente,
                                                      @Param("especialidad") String especialidad);

    // Contar citas activas (no finalizadas/canceladas) por paciente
    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.paciente = :paciente AND c.estado NOT IN ('FINALIZADA', 'CANCELADA')")
    long countCitasActivasByPaciente(@Param("paciente") Usuario paciente);

    // Contar citas activas en un rango de fechas
    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.paciente = :paciente AND c.estado NOT IN ('FINALIZADA', 'CANCELADA') AND c.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    long countCitasActivasByPacienteAndFechaRange(@Param("paciente") Usuario paciente,
                                                  @Param("fechaInicio") LocalDateTime fechaInicio,
                                                  @Param("fechaFin") LocalDateTime fechaFin);

    // Verificar conflicto de horario para el mismo paciente
    @Query("SELECT COUNT(c) > 0 FROM CitaMedica c WHERE c.paciente = :paciente AND c.fechaHora = :fechaHora AND c.estado NOT IN ('FINALIZADA', 'CANCELADA')")
    boolean existsCitaConflictoByPacienteAndFecha(@Param("paciente") Usuario paciente,
                                                  @Param("fechaHora") LocalDateTime fechaHora);

    // Obtener citas activas por paciente
    @Query("SELECT c FROM CitaMedica c WHERE c.paciente = :paciente AND c.estado NOT IN ('FINALIZADA', 'CANCELADA')")
    List<CitaMedica> findCitasActivasByPaciente(@Param("paciente") Usuario paciente);

    // Obtener citas PENDIENTES por médico (excluye las finalizadas)
    @Query("SELECT c FROM CitaMedica c WHERE c.medico.id = :medicoId AND c.estado = 'PENDIENTE' ORDER BY c.fechaHora ASC")
    List<CitaMedica> findCitasPendientesByMedico(@Param("medicoId") Long medicoId);

    // Obtener citas por paciente ID
    @Query("SELECT c FROM CitaMedica c WHERE c.paciente.id = :pacienteId AND c.estado NOT IN ('FINALIZADA', 'CANCELADA')")
    List<CitaMedica> findCitasActivasByPacienteId(@Param("pacienteId") Long pacienteId);
}