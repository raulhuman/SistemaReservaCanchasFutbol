/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.ConexionDB;
import VO.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;


public class ReservaDAO implements IReserva{
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void insertarReserva(Reserva reserva) {
        
        try {
            conn=ConexionDB.MySQL8();
            ps=conn.prepareStatement("insert into reserva values(default,?,?,?,?,(select idCliente from cliente where dni=?))");
            
            ps.setString(1, reserva.getCancha());
            ps.setString(2, reserva.getFechaInscripcion());
            ps.setString(3, reserva.getFechaInicio());
            ps.setString(4, reserva.getFechaFin());
            ps.setString(5, reserva.getCliente());
            
            int rows = ps.executeUpdate();
            
            if(rows!=1) {
                System.out.println("¡Error INSERT!");
            }
                     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Reserva> listarReserva() {
        Collection<Reserva> lista = new ArrayList<>();

        try {
            conn = ConexionDB.MySQL8();
            ps = conn.prepareStatement("select * from reserva");

            rs = ps.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();

                reserva.setCancha(rs.getString("cancha"));
                reserva.setFechaInicio(rs.getString("fechaInicio"));
                reserva.setFechaFin(rs.getString("fechaFin"));

                lista.add(reserva);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    @Override
    public Reserva buscarDni(String dni) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarReserva(Reserva reserva) {
        
        try {
            conn = ConexionDB.MySQL8();
            String SQL = "update reserva set cancha=?,fechaInscripcion=?,fechaInicio=?,fechaFin=? where idreserva=?";
            ps = conn.prepareCall(SQL);
            
            ps.setString(1, reserva.getCancha());
            ps.setString(2, reserva.getFechaInscripcion());
            ps.setString(3, reserva.getFechaInicio());
            ps.setString(4, reserva.getFechaFin());
            ps.setInt(5, reserva.getIdReserva());
            
            int rows = ps.executeUpdate();
            if (rows != 1) {
                System.out.println("Error UPDATE!");
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer idreserva) {
        try {
            conn = ConexionDB.MySQL8();

            String SQL = "delete from reserva where idreserva=?";
            ps = conn.prepareCall(SQL);

            ps.setInt(1, idreserva);

            int rows = ps.executeUpdate();
            if (rows != 1) {
                System.out.println("Error DELETE!");
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
