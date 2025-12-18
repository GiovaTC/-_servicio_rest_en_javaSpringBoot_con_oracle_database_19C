package com.example.restoracleservice.service;

import com.example.restoracleservice.model.VentaRequest;
import com.example.restoracleservice.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public void procesarVenta(VentaRequest request) {
        ventaRepository.registrarVenta(request);
    }
}
