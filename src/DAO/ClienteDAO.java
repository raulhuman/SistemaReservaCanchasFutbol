/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.ConexionDB;
import VO.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO implements ICliente{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public Cliente buscarDNI(String dni) {
        
        Cliente cl = null;
        
        try {
            conn=ConexionDB.MySQL8();
            ps = conn.prepareCall("select * from cliente where dni=?");
            
            ps.setString(1, dni);
            rs=ps.executeQuery();
            
            if(rs.next()){
                cl = new Cliente();
                
                cl.setIdCliente(rs.getInt("idCliente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellidos"));
                cl.setDni(rs.getString("dni"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setEmail(rs.getString("email"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cl;
    }

    @Override
    public void agregar(Cliente cliente) {
        
        try {
            conn=ConexionDB.MySQL8();
            ps=conn.prepareStatement("insert into cliente values(default,?,?,?,?,?)");
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            
            int rows = ps.executeUpdate();
            
            if(rows!=1) {
                System.out.println("¡Error INSERT!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
