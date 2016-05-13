/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author Baxes
 */
public class Departamento{
    private final int id;
    private int numTropas;
    private int idJugador;

    public Departamento(int id){
        this.id = id;
        numTropas = 0;
        idJugador = 0;
    }

    public int getId(){
        return id;
    }

    public int getNumTropas(){
        return numTropas;
    }

    public void addTropas(){
        this.numTropas++;
    }
    
    public void reduceTropas(){
        this.numTropas--;
    }

    public int getIdJugador(){
        return idJugador;
    }

    public void setIdJugador(int idJugador){
        this.idJugador = idJugador;
    }

    @Override
    public String toString(){
        return "Departamento{" + "id=" + id + ", numTropas=" + numTropas + ", idJugador=" + idJugador + '}';
    }
    
    
}
