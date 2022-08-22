/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.ConexionDB;
import VO.Comprobante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class ComprobanteDAO implements IComprobante{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void nuevo(Comprobante comprobante) {
        
        try {
            conn=ConexionDB.MySQL8();
            ps=conn.prepareStatement("insert into comprobante values(default,?,?,(select idCliente from cliente where dni=?),(select idproducto from producto where nomProducto=?))");
            
            ps.setInt(1, comprobante.getCantidad());
            ps.setDate(2, new java.sql.Date(comprobante.getFecha().getTime()));
            ps.setString(3, comprobante.getCliente());
            ps.setString(4, comprobante.getProducto());
            
            int rows = ps.executeUpdate();
            
            if(rows!=1) {
                System.out.println("¡Error INSERT!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(Comprobante comprobante) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer idComprobante) {
        try {
            conn = ConexionDB.MySQL8();
            ps=conn.prepareCall("delete from comprobante where idcomprobante=?");
            ps.setInt(1, idComprobante);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Comprobante findbyId(Integer idComprobante) {
        Comprobante com = null;
        
        try {
           conn=ConexionDB.MySQL8();
           ps = conn.prepareCall("select * from comprobante where idcomprobante=?");
           
           ps.setInt(1, idComprobante);
           rs=ps.executeQuery();
           
           if(rs.next()){
               com = new Comprobante();
               
               com.setIdComprobante(rs.getInt("idcomprobante"));
               com.setCantidad(rs.getInt("cantidad"));
               com.setFecha(rs.getDate("fecha_alquiler"));
               com.setCliente(rs.getString("idCliente"));
               com.setProducto(rs.getString("idProducto"));
           }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return com;
    }

    @Override
    public Collection<Comprobante> findAll() {
        Collection<Comprobante> lista = new ArrayList<>();
        
        try {
            conn=ConexionDB.MySQL8();
            ps=conn.prepareStatement("select * from comprobante");
            
            rs=ps.executeQuery();
            
            while(rs.next()){
                Comprobante com = new Comprobante();
                
                com.setIdComprobante(rs.getInt("idcomprobante"));
                com.setCantidad(rs.getInt("cantidad"));
                com.setFecha(rs.getDate("fecha_alquiler"));
                com.setCliente(rs.getString("idCliente"));
                com.setProducto(rs.getString("idProducto"));
                
                
                lista.add(com);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Comprobante generarBoleta(String dni) {
        Comprobante com = null;
        try {
            conn=ConexionDB.MySQL8();
            ps = conn.prepareCall("select idcomprobante, nombre, apellidos, nomProducto, cantidad"
                    + "from comprobante inner join cliente"
                    + "on comprobante.idCliente = cliente.idCliente"
                    +"inner join producto on comprobante.idProducto = producto.idproducto"
                    + " where cliente.dni=?");
            ps.setString(1, dni);
            rs=ps.executeQuery();
            
            if(rs.next()){
               com = new Comprobante();
               
               com.setIdComprobante(rs.getInt("idcomprobante"));
               com.setCantidad(rs.getInt("cantidad"));
               com.setFecha(rs.getDate("fecha_alquiler"));
               com.setCliente(rs.getString("idCliente"));
               com.setProducto(rs.getString("idProducto"));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return com;
    }
    
}
