/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Reserva;
import java.util.ArrayList;
import java.util.Collection;


public interface IReserva {
    public abstract void insertarReserva(Reserva reserva);
    public abstract Collection<Reserva> listarReserva();
    public abstract Reserva buscarDni(String dni);
    public abstract void editarReserva(Reserva reserva);
    public abstract void delete(Integer idreserva);
    
}
