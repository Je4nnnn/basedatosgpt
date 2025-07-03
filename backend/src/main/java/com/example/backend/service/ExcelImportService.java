package com.example.backend.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.model.Usuario;
import com.example.backend.model.Medicamento;
import com.example.backend.model.StockMedicamento;
import com.example.backend.model.CitaMedica;
import com.example.backend.dto.RecetaMedicaDTO;
import com.example.backend.dto.HistorialMedicoDTO;
import com.example.backend.service.UsuarioService;
import com.example.backend.service.MedicamentoService;
import com.example.backend.service.StockMedicamentoService;
import com.example.backend.service.CitaMedicaService;
import com.example.backend.service.RecetaMedicaService;
import com.example.backend.service.HistorialMedicoService;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@Service
public class ExcelImportService {

    private final UsuarioService usuarioService;
    private final MedicamentoService medicamentoService;
    private final CitaMedicaService citaMedicaService;
    private final RecetaMedicaService recetaMedicaService;
    private final HistorialMedicoService historialMedicoService;
    private final StockMedicamentoService stockService;

    public ExcelImportService(UsuarioService usuarioService,
                              MedicamentoService medicamentoService,
                              CitaMedicaService citaMedicaService,
                              RecetaMedicaService recetaMedicaService,
                              HistorialMedicoService historialMedicoService,
                              StockMedicamentoService stockService) {
        this.usuarioService = usuarioService;
        this.medicamentoService = medicamentoService;
        this.citaMedicaService = citaMedicaService;
        this.recetaMedicaService = recetaMedicaService;
        this.historialMedicoService = historialMedicoService;
        this.stockService = stockService;
    }

    public void processExcel(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook wb = new XSSFWorkbook(is)) {

            for (Sheet sheet : wb) {
                String name = sheet.getSheetName().trim().toLowerCase();
                Iterator<Row> rows = sheet.iterator();
                if (!rows.hasNext()) continue;
                rows.next(); // saltar cabecera

                while (rows.hasNext()) {
                    Row row = rows.next();
                    switch (name) {
                        case "usuarios":
                            importarUsuario(row);
                            break;
                        case "medicamentos":
                            importarMedicamento(row);
                            break;
                        case "stock":
                        case "stock_de_medicamentos":
                            importarStock(row);
                            break;
                        case "citas_medicas":
                            importarCita(row);
                            break;
                        case "recetas":
                            importarReceta(row);
                            break;
                        case "historial_medico":
                        case "registro_de_historiales_medicos":
                            importarHistorial(row);
                            break;
                        default:
                            // hoja ignorada
                    }
                }
            }
        }
    }

    private void importarUsuario(Row row) {
        Usuario u = new Usuario();
        u.setNombre(   row.getCell(0).getStringCellValue() );
        u.setEmail(    row.getCell(1).getStringCellValue() );
        u.setPassword( row.getCell(2).getStringCellValue() );
        u.setRol(      row.getCell(3).getStringCellValue() );
        usuarioService.crearUsuario(u);
    }

    private void importarMedicamento(Row row) {
        Medicamento m = new Medicamento();
        m.setNombre(      row.getCell(0).getStringCellValue() );
        m.setDescripcion( row.getCell(1).getStringCellValue() );
        medicamentoService.registrarMedicamento(m);
    }

    private void importarStock(Row row) {
        String nombre = row.getCell(0).getStringCellValue();
        int cantidad   = (int) row.getCell(1).getNumericCellValue();
        Medicamento med = medicamentoService.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Medicamento no existe: " + nombre));
        StockMedicamento stk = new StockMedicamento();
        stk.setMedicamento(med);
        stk.setCantidadDisponible(cantidad);
        stockService.actualizarStock(stk);
    }

    private void importarCita(Row row) {
        LocalDateTime fecha = LocalDateTime.parse(
                row.getCell(0).getStringCellValue(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        );
        Long pacienteId = (long) row.getCell(1).getNumericCellValue();
        Long medicoId   = (long) row.getCell(2).getNumericCellValue();
        String motivo   = row.getCell(3).getStringCellValue();
        String esp      = row.getCell(4).getStringCellValue();

        CitaMedica cita = new CitaMedica();
        cita.setFechaHora(fecha);
        cita.setMotivo(motivo);
        cita.setEspecialidad(esp);
        cita.setEstado("AGENDADA");
        cita.setPaciente(usuarioService.buscarUsuarioPorId(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no existe: " + pacienteId)));
        cita.setMedico(usuarioService.buscarUsuarioPorId(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no existe: " + medicoId)));
        citaMedicaService.agendarCita(cita);
    }

    private void importarReceta(Row row) {
        // columnas: 0=descripcion, 1=pacienteId, 2=nombreMedicamento, 3=medicoId
        RecetaMedicaDTO dto = new RecetaMedicaDTO();
        dto.setDescripcion(      row.getCell(0).getStringCellValue() );
        dto.setPacienteId(       (long) row.getCell(1).getNumericCellValue() );
        dto.setNombreMedicamento(row.getCell(2).getStringCellValue());
        Long medicoId = (long) row.getCell(3).getNumericCellValue();
        recetaMedicaService.registrarReceta(dto, medicoId);
    }

    private void importarHistorial(Row row) {
        HistorialMedicoDTO dto = new HistorialMedicoDTO();
        dto.setDiagnostico( row.getCell(0).getStringCellValue() );
        dto.setPacienteId(  (long) row.getCell(1).getNumericCellValue() );
        historialMedicoService.registrarHistorial(dto);
    }
}
