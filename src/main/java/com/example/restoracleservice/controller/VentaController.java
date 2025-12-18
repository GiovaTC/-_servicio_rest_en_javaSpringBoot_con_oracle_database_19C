package com.example.restoracleservice.controller;

import com.example.restoracleservice.model.VentaRequest;
import com.example.restoracleservice.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request){
        ventaService.procesarVenta(request);
        return ResponseEntity.ok("venta registrada exitosamente en oracle 19C .");
    }
}
