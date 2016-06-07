/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Juego.Juego;
import Juego.RiskException;

/**
 *
 * @author Baxes
 */
public class Risk extends JFrame{
    private PanelJugadores jugadores;
    private PanelJuego juego;
    private Juego jugar;
    private int jugadorActual;
    private Msgbox msg;
    
    public Risk(int numJugadores){
        this.setTitle("Colombia Conquest");
        this.setSize(1024,700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try{
            this.jugar = new Juego(numJugadores, this);
        } catch(FileNotFoundException e) {
            dispose();
            JOptionPane.showMessageDialog(this, "dptos_adyacencia.txt no "
                    + "existe en la carpeta del juego.", "Error al leer "
                    + "archivos", JOptionPane.WARNING_MESSAGE);
        } catch(RiskException e) {
            dispose();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);
        } catch(IOException e){
            dispose();
            JOptionPane.showMessageDialog(this, "Error al leer archivos del juego", "Error", JOptionPane.WARNING_MESSAGE);
        }

        setLayout(new BorderLayout());



        juego = new PanelJuego(this);
        add(juego, BorderLayout.CENTER);
        jugadores = new PanelJugadores(numJugadores, this);
        add(jugadores, BorderLayout.LINE_END);
        
        setVisible(true);

        //Test commands
        jugar.setJugador(0, 0);
        jugar.setJugador(1, 1);
        jugar.setJugador(6, 2);
        jugar.setJugador(5, 3);
        jugar.setJugador(8, 1);
        jugar.setJugador(9, 1);
        jugar.setJugador(11, 3);
        //7 6 9 10 12
        jugar.addTropas(0, 10);
        jugar.addTropas(1, 1);
        jugar.addTropas(6, 1);
        jugar.addTropas(5, 1);
        jugar.addTropas(8, 1);
        jugar.addTropas(9, 1);
        jugar.addTropas(11, 1);
        update();

        agregarCarta(0);
        agregarCarta(0);
        agregarCarta(0);
        agregarCarta(0);
        agregarCarta(0);
    }

    public void update(){
        juego.update();
    }
    
    public void comprobarAtaque(int atk, int target, int jugador)
            throws RiskException{
        jugar.comprobarAtaque(atk, target, jugador);
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        jugar.atacar(atk, target, jugador, dado1, dado2);
    }

    public void print(String message){
        msg.print(message);
    }

    public int cambiarFase(){
        return jugar.cambiarFase();
    }

    public void moverTropas(int idA, int idB, int cantidad){
        jugar.moverTropas(idA, idB, cantidad);
    }

    public boolean checkConquista(int target, int jugador){
        return jugar.checkConquista(target,jugador);
    }

    public boolean checkTerritorio(int dpto, int jugador){
        return jugar.checkTerritorio(dpto, jugador);
    }

    public int seleccionarCarta(int posCarta, int jugador){
        return jugar.seleccionarCarta(posCarta, jugador);
    }

    public void deseleccionarCarta(int posCarta, int jugador){
        jugar.deseleccionarCarta(posCarta, jugador);
    }

    public boolean checkContCartas(int jugador){
        return jugar.checkContCartas(jugador);
    }

    public void agregarCarta(int jugador){
        int carta = (int)(Math.random()*4);
        jugadores.agregarCarta(jugador, carta);
        jugar.addCarta(jugador, carta);
    }

    public ArrayList<Integer> getCartas(int jugador){
        return jugar.getCartas(jugador);
    }

    public int getJugadorDpto(int idDpto){
        return jugar.getDpto(idDpto)[0];
    }

    public int getTropasDpto(int idDpto){
        return jugar.getDpto(idDpto)[1];
    }

    public String getNombreDpto(int idDpto){
        return jugar.getNombreDpto(idDpto);
    }

    public String getRegionDpto(int idDpto){
        return jugar.getRegionDpto(idDpto);
    }

    public void addTropasDpto(int idDpto, int cantidad){
        jugar.addTropas(idDpto, cantidad);
    }

    public void reduceTropasDpto(int idDpto, int cantidad){
        jugar.reduceTropas(idDpto,cantidad);
    }

    public void updateTropas(int jugador){
        jugadores.updateTropas(jugador);
    }

    public void setMsgbox(Msgbox msg){
        this.msg = msg;
    }
}
