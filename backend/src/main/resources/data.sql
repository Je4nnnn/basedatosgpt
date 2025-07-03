-- Primero agregar la columna especialidad a usuarios
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS especialidad VARCHAR(100);

-- Insertar usuarios (4 pacientes + 4 médicos con sus especialidades)
INSERT INTO usuarios (id, nombre, email, password, rol, especialidad) VALUES
(1, 'Juan Pérez', 'juan.perez@example.com', '1234', 'PACIENTE', NULL),
(2, 'María González', 'maria.gonzalez@example.com', '1234', 'PACIENTE', NULL),
(3, 'Pedro López', 'pedro.lopez@example.com', '1234', 'PACIENTE', NULL),
(4, 'Ana Díaz', 'ana.diaz@example.com', '1234', 'PACIENTE', NULL),
(5, 'Dr. Rodrigo Salas', 'rodrigo.salas@example.com', 'abcd1234', 'MEDICO', 'Medicina General'),
(6, 'Dra. Carmen Vidal', 'carmen.vidal@example.com', 'abcd1234', 'MEDICO', 'Cardiología'),
(7, 'Dr. Luis Fernández', 'luis.fernandez@example.com', 'abcd1234', 'MEDICO', 'Medicina General'),
(8, 'Dra. Paula Martínez', 'paula.martinez@example.com', 'abcd1234', 'MEDICO', 'Pediatría');

-- Insertar disponibilidad médica (tabla correcta: disponibilidades_medico)
INSERT INTO disponibilidades_medico (medico_id, fecha_hora_inicio, fecha_hora_fin, disponible) VALUES
(5, '2026-05-29 09:00:00', '2026-05-29 10:00:00', true),
(5, '2026-05-28 10:00:00', '2026-05-28 11:00:00', true),
(6, '2026-05-27 11:00:00', '2026-05-27 12:00:00', true),
(6, '2026-05-26 12:00:00', '2026-05-26 13:00:00', true);

-- Insertar medicamentos
INSERT INTO medicamentos (id, nombre, descripcion) VALUES
(1, 'Paracetamol', '500mg'),
(2, 'Ibuprofeno', '400mg'),
(3, 'Amoxicilina', '500mg');

UPDATE medicamentos
SET descripcion = ''
WHERE descripcion IS NULL;

-- Insertar stock de medicamentos
INSERT INTO stock_medicamentos (cantidad_disponible, medicamento_id) VALUES
(100, 1),
(50, 2),
(80, 3);

-- Actualizar secuencia de usuarios
SELECT setval(
  'usuarios_id_seq',
  (SELECT COALESCE(MAX(id), 1) FROM usuarios)
);

SELECT setval(
  'medicamentos_id_seq',
  (SELECT MAX(id) FROM medicamentos)
);

-- Insertar citas médicas (usando la especialidad del médico)
INSERT INTO citas_medicas (paciente_id, medico_id, fecha_hora, motivo, estado, especialidad) VALUES
(1, 5, '2025-08-20 10:00:00', 'Consulta general', 'PENDIENTE', 'Medicina General'),
(2, 6, '2025-08-21 11:00:00', 'Seguimiento', 'PENDIENTE', 'Cardiología'),
(1, 7, '2025-08-15 09:00:00', 'Consulta pasada', 'FINALIZADA', 'Medicina General');
-- Actualizar citas existentes para tener estado por defecto
--UPDATE citas_medicas SET estado = 'PENDIENTE' WHERE estado IS NULL OR estado = '';
