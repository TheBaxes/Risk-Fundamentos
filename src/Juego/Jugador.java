/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.util.ArrayList;

/**
 *
 * @author Baxes
 */
public class Jugador {
    private final int id;
    private int tropas;
    private int tipoCarta;
    private int contCartas;
    private final ArrayList<Integer> cartas;
    
    public Jugador(int idJugador){
        this.id = idJugador;
        tropas = 0;
        tipoCarta = 0;
        contCartas = 0;
        cartas = new ArrayList<>(5);
    }

    public int getId(){
        return id;
    }

    public int getTropas(){
        return tropas;
    }

    public void addTropas(int tropas){
        this.tropas += tropas;
    }
    
    public void removeTropas(int tropas) throws RiskException{
        this.tropas -= tropas;
        if(this.tropas < 0){
            throw new RiskException("Tropas negativas");
        }
    }

    public int getTipoCarta(){
        return tipoCarta;
    }

    public void setTipoCarta(int tipoCarta){
        this.tipoCarta = tipoCarta;
    }
    
    public int getContCartas(){
        return contCartas;
    }

    public void addContCartas(){
        this.contCartas++;
    }
    
    public void resetContCartas(){
        this.contCartas = 0;
    }

    public int getCarta(int posCarta){
        return cartas.get(posCarta);
    }

    public void addCarta(int carta){
        this.cartas.add(carta);
    }
    
    public void removeCarta(int posCarta){
        this.cartas.remove(posCarta);
    }    
    
}
