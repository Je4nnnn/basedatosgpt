package com.example.backend.controller;

import com.example.backend.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:5173")  // Ajustar según tu frontend :contentReference[oaicite:0]{index=0}
public class AdminController {

    @Autowired
    private ExcelImportService importService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            importService.processExcel(file);
            return ResponseEntity.ok("Importación completada correctamente");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al procesar Excel: " + e.getMessage());
        }
    }
}
