package com.example.restoracleservice.repository;

import com.example.restoracleservice.model.VentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VentaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registrarVenta(VentaRequest request) {
        jdbcTemplate.update(
                "CALL SP_REGISTRAR_VENTA(?,?,?,?,?,?,?)",
                request.getCliente().getIdCliente(),
                request.getCliente().getNombre(),
                request.getCliente().getEmail(),
                request.getProducto().getIdProducto(),
                request.getProducto().getNombre(),
                request.getProducto().getPrecio(),
                request.getDetalleProducto()
        );
    }
}
