/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

/**
 *
 * @author Baxes
 */
public class Departamento{
    private final int id;
    private String nombre;
    private int numTropas;
    private int idJugador;

    public Departamento(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        numTropas = 0;
        idJugador = -1;
    }

    public int getId(){
        return id;
    }

    public int getNumTropas(){
        return numTropas;
    }

    public void addTropas(int cantidad){
        this.numTropas += cantidad;
    }
    
    public void reduceTropas(int cantidad){
        this.numTropas -= cantidad;
    }

    public int getIdJugador(){
        return idJugador;
    }

    public void setIdJugador(int idJugador){
        this.idJugador = idJugador;
    }

    public String getNombre(){
        return nombre;
    }

    @Override
    public String toString(){
        return "Departamento{" + "id=" + id + ", numTropas=" + numTropas + ", idJugador=" + idJugador + '}';
    }
    
    
}
