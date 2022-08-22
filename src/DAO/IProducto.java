/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Producto;
import java.util.Collection;

public interface IProducto {
    
    public abstract Producto buscarProducto(String nombre);
    public abstract Collection<Producto> listaProducto();
    public abstract void actualizarStock(Producto producto);
    
}
