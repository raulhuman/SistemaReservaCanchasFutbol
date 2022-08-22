/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import java.util.Date;


public class Comprobante {
    
    private Integer idComprobante;
    private Integer cantidad;
    private Date fecha;
    private String cliente;
    private String producto;

    public Comprobante() {
    }

    public Comprobante(Integer idComprobante, Integer cantidad, Date fecha, String cliente, String producto) {
        this.idComprobante = idComprobante;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.cliente = cliente;
        this.producto = producto;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    
    
}
