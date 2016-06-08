/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Clase jugador
 * @author Sebastián Patiño Barrientos
 */
public class Jugador{
    private final int id;
    private int tropas;
    private int contCartas;
    private ArrayList<Integer> cartas;
    private boolean[] cartasSel;
    private ArrayList<Integer> cartasClick;
    private int cartasComodin;
    private int dptos;

    /**
     * Crea un objeto jugador
     * @param idJugador Número del jugador
     */
    public Jugador(int idJugador){
        this.id = idJugador;
        contCartas = 0;
        cartas = new ArrayList<>(5);
        cartasSel = new boolean[5];
        cartasClick = new ArrayList<>(5);
        dptos = 0;
    }

    /**
     * Método que retorna el id del jugador
     * @return int con el número del jugador
     */
    public int getId(){
        return id;
    }

    /**
     * Método que retorna la cantidad de tropas del jugador
     * @return int con la cantidad
     */
    public int getTropas(){
        return tropas;
    }

    /**
     * Método que adiciona tropas al jugador
     * @param tropas Cantidad de tropas
     */
    public void addTropas(int tropas){
        this.tropas += tropas;
    }

    /**
     * Método que remueve tropas al jugador
     * @param tropas Cantidad de tropas
     */
    public void removeTropas(int tropas){
        this.tropas -= tropas;
    }

    /**
     * Método que comprueba si las cartas seleccionadas son usables y en caso de que lo sean las usa
     * @return booleano que es verdadero si se usaron cartas
     */
    public boolean checkCartas(){
        boolean check = false;
        if (cartasComodin == 0) {
            if ((cartasClick.get(0) == cartasClick.get(1) && cartasClick.get(1) == cartasClick.get(2)
                    && cartasClick.get(2) == cartasClick.get(0))) {
                check = true;
            } else if ((cartasClick.get(0) != cartasClick.get(1)
                    && cartasClick.get(1) != cartasClick.get(2) && cartasClick.get(2) != cartasClick.get(0))) {
                check = true;
            }
        } else {
            check = true;
        }

        if(check){
            contCartas = 0;
            addTropas(7);
            int remove = 0;
            for (int i = 0; i < 5; i++) {
                if(cartasSel[i]){
                    cartasSel[i] = false;
                    cartas.remove(i - remove);
                    remove++;
                }
            }
            cartasClick.clear();
        }
        return check;
    }

    /**
     * Método que selecciona una carta
     * @param posCarta Posición de la carta
     * @return Valor de la carta en esa posición
     */
    public int seleccionarCarta(int posCarta){
        contCartas++;
        int id = cartas.get(posCarta);
        if(id < 3){
            cartasClick.add(id);
        } else {
            cartasComodin++;
        }
        cartasSel[posCarta] = true;
        return contCartas;
    }

    /**
     * Método que deselecciona una carta
     * @param posCarta Posición de la carta
     */
    public void deseleccionarCarta(int posCarta){
        contCartas--;
        int id = cartas.get(posCarta);
        if(id < 3){
            cartasClick.remove((Integer)id);
        } else {
            cartasComodin--;
        }
        cartasSel[posCarta] = false;
    }

    /**
     * Método que agrega una carta al jugador
     * @param carta Carta a agregar
     */
    public void addCarta(int carta){
        this.cartas.add(carta);
    }

    /**
     * Método que retorna las cartas que posee el jugador
     * @return ArrayList con las cartas del jugador
     */
    public ArrayList<Integer> getCartas(){
        return cartas;
    }

    /**
     * Método que aumenta el contador de departamentos del jugador
     */
    public void addDptos(){
        dptos++;
    }

    /**
     * Método que disminuye el contador de departamentos del jugador
     */
    public void removeDptos(){
        dptos--;
    }

    /**
     * Método que retorna el contador de departamentos del jugador
     * @return int con el valor del contador
     */
    public int getDptos(){
        return dptos;
    }
}
