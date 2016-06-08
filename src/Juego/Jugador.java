/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Baxes
 */
public class Jugador{
    private final int id;
    private int tropas;
    private int contCartas;
    private ArrayList<Integer> cartas;
    private boolean[] cartasSel;
    private ArrayList<Integer> cartasClick;
    private int cartasComodin;
    
    public Jugador(int idJugador){
        this.id = idJugador;
        contCartas = 0;
        cartas = new ArrayList<>(5);
        cartasSel = new boolean[5];
        cartasClick = new ArrayList<>(5);
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
    
    public boolean checkContCartas(){
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

    public void addCarta(int carta){
        this.cartas.add(carta);
    }
    
    public void removeCarta(int posCarta){
        this.cartas.remove(posCarta);
    }    

    public ArrayList<Integer> getCartas(){
        return cartas;
    }
}
