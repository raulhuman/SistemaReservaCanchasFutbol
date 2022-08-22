/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Cliente;

public interface ICliente {
    
    public abstract Cliente buscarDNI(String dni);
    
    public abstract void agregar(Cliente cliente);
    
}
