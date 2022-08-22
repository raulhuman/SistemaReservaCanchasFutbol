/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Comprobante;
import java.util.Collection;

public interface IComprobante {
    
    public abstract void nuevo(Comprobante comprobante);
    public abstract void update(Comprobante comprobante);
    public abstract void delete(Integer idComprobante);
    public abstract Comprobante findbyId(Integer idComprobante);
    public abstract Collection<Comprobante> findAll();
    
    public abstract Comprobante generarBoleta(String dni);
    
}
