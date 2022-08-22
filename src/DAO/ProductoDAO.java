/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.ConexionDB;
import VO.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class ProductoDAO implements IProducto{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public Producto buscarProducto(String nombre) {
        Producto producto = null;
        
        try {
            conn=ConexionDB.MySQL8();
            ps = conn.prepareCall("select * from producto where nomProducto=?");
            
            ps.setString(1, nombre);
            rs=ps.executeQuery();
            
            if(rs.next()){
                producto = new Producto();
                
                producto.setIdProducto(rs.getInt("idproducto"));
                producto.setNomProducto(rs.getString("nomProducto"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio_alquiler(rs.getDouble("precio_alquiler"));
                producto.setMarca(rs.getString("marca"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public Collection<Producto> listaProducto() {
        Collection<Producto> lista = new ArrayList<>();
        
        try {
            conn=ConexionDB.MySQL8();
            ps=conn.prepareStatement("select * from producto");
            
            rs=ps.executeQuery();
            
            while (rs.next()) {
                Producto producto = new Producto();
                
                producto.setIdProducto(rs.getInt("idproducto"));
                producto.setNomProducto(rs.getString("nomProducto"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio_alquiler(rs.getDouble("precio_alquiler"));
                producto.setMarca(rs.getString("marca"));
                
                lista.add(producto);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizarStock(Producto producto) {
        try {
            conn = ConexionDB.MySQL8();
            String SQL = "update producto set stock=? where idproducto=?";
            ps = conn.prepareCall(SQL);
            
            ps.setInt(1, producto.getStock());
            ps.setInt(2, producto.getIdProducto());
            
            int rows = ps.executeUpdate();
            if (rows != 1) {
                System.out.println("Error UPDATE!");
            }
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
