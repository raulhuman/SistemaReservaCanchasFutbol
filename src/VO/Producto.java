/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

public class Producto {
    
    private Integer idProducto;
    private String nomProducto;
    private Integer stock;
    private Double precio_alquiler;
    private String marca;

    public Producto() {
    }

    public Producto(Integer idProducto, String nomProducto, Integer stock, Double precio_alquiler, String marca) {
        this.idProducto = idProducto;
        this.nomProducto = nomProducto;
        this.stock = stock;
        this.precio_alquiler = precio_alquiler;
        this.marca = marca;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio_alquiler() {
        return precio_alquiler;
    }

    public void setPrecio_alquiler(Double precio_alquiler) {
        this.precio_alquiler = precio_alquiler;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    
}
