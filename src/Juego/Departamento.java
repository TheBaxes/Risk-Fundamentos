/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

/**
 * Clase departamento
 * @author Sebastián Patiño Barrientos
 */
public class Departamento{
    private final int id;
    private String nombre;
    private String region;
    private int numTropas;
    private int idJugador;

    /**
     * Crea un nuevo objeto departamento
     * @param id Id del departamento
     * @param nombre Nombre del departamento
     * @param region Region del departamento
     */
    public Departamento(int id, String nombre, String region){
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        numTropas = 0;
        idJugador = -1;
    }

    /**
     * Método que retorna el id del departamento
     * @return int con el id del departamento
     */
    public int getId(){
        return id;
    }

    /**
     * Método que retorna el número de tropas del departamento
     * @return int con la cantidad de tropas
     */
    public int getNumTropas(){
        return numTropas;
    }

    /**
     * Método que adiciona tropas a un departamento
     * @param cantidad Cantidad a aumentar
     */
    public void addTropas(int cantidad){
        this.numTropas += cantidad;
    }

    /**
     * Método que reduce la cantidad de tropas de un departamento
     * @param cantidad Cantidad a disminuir
     */
    public void reduceTropas(int cantidad){
        this.numTropas -= cantidad;
    }

    /**
     * Método que retorna el ID del jugador asociado al departamento
     * @return int con el número de jugador
     */
    public int getIdJugador(){
        return idJugador;
    }

    /**
     * Método que le asigna un jugador al departamento
     * @param idJugador número del jugador
     */
    public void setIdJugador(int idJugador){
        this.idJugador = idJugador;
    }

    /**
     * Método que retorna el nombre del departamento
     * @return String con el nombre
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Método que retorna la región del departamento
     * @return String con la region
     */
    public String getRegion(){
        return region;
    }
}
