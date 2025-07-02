// backend/src/main/java/com/example/backend/repository/DisponibilidadMedicoRepository.java
package com.example.backend.repository;

import com.example.backend.model.DisponibilidadMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadMedicoRepository extends JpaRepository<DisponibilidadMedico, Long> {

    List<DisponibilidadMedico> findByMedicoIdAndDisponibleTrue(Long medicoId);
}

