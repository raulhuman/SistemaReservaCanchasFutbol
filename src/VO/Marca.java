/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

public class Marca {
    
    private Integer idMarca;
    private String nomMarca;

    public Marca() {
    }

    public Marca(Integer idMarca, String nomMarca) {
        this.idMarca = idMarca;
        this.nomMarca = nomMarca;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNomMarca() {
        return nomMarca;
    }

    public void setNomMarca(String nomMarca) {
        this.nomMarca = nomMarca;
    }
    
    
    
}
