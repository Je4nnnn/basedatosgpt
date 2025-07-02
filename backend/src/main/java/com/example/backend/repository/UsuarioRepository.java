package com.example.backend.repository;

import com.example.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRol(String rol);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmailAndPassword(String email, String password);

    // 🔥 NUEVOS MÉTODOS PARA ESPECIALIDAD - AGREGAR ESTOS
    List<Usuario> findByRolAndEspecialidad(String rol, String especialidad);

    List<Usuario> findByEspecialidad(String especialidad);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'MEDICO' AND u.especialidad = ?1")
    List<Usuario> findMedicosByEspecialidad(String especialidad);

    // Método más flexible para búsqueda por especialidad (ignora mayúsculas/minúsculas)
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'MEDICO' AND LOWER(u.especialidad) = LOWER(?1)")
    List<Usuario> findMedicosByEspecialidadIgnoreCase(String especialidad);
}