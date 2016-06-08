/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

/**
 * Excepción personalizada
 * @author Sebastián Patiño Barrientos
 */
public class RiskException extends Exception{
    /**
     * Crea una excepción personalizada para el juego
     * @param message Mensaje a mostrar
     */
    public RiskException(String message){
        super(message);
    }
}
