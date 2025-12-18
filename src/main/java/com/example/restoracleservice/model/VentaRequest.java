package com.example.restoracleservice.model;

public class VentaRequest {

    private Cliente cliente;
    private Producto producto;
    private String detalleProducto;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getDetalleProducto() {
        return detalleProducto;
    }

    public void setDetalleProducto(String detalleProducto) {
        this.detalleProducto = detalleProducto;
    }
}
