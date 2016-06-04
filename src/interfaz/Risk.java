/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

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
    
    public Risk(int numJugadores){
        this.setTitle("Colombia Conquest");
        this.setSize(1024,700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try{
            this.jugar = new Juego(numJugadores);
        } catch(FileNotFoundException e) {
            dispose();
            JOptionPane.showMessageDialog(this, "dptos_adyacencia.txt no "
                    + "existe en la carpeta del juego.", "Error al leer "
                    + "archivos", JOptionPane.WARNING_MESSAGE);
        } catch(RiskException e) {
            dispose();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);
        }

        setLayout(new BorderLayout());



        juego = new PanelJuego(this);
        add(juego, BorderLayout.CENTER);
        jugadores = new PanelJugadores(numJugadores);
        add(jugadores, BorderLayout.LINE_END);
        
        setVisible(true);

        //Test commands
        jugar.setJugador(0, 1);
        jugar.setJugador(1, 2);
        jugar.addTropas(0, 10);
        jugar.addTropas(1, 3);
        update();
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

    public void moverTropas(int idA, int idB, int cantidad){
        jugar.moverTropas(idA, idB, cantidad);
    }

    public boolean checkConquista(int target, int jugador){
        return jugar.checkConquista(target,jugador);
    }

    public int getJugadorDpto(int idDpto){
        return jugar.getDpto(idDpto)[0];
    }

    public int getTropasDpto(int idDpto){
        return jugar.getDpto(idDpto)[1];
    }

    public void addTropasDpto(int idDpto, int cantidad){
        jugar.addTropas(idDpto, cantidad);
    }

    public void reduceTropasDpto(int idDpto, int cantidad){
        jugar.reduceTropas(idDpto,cantidad);
    }

    public void setTipoCartaJugador(int id, int carta){
        jugar.setTipoCartaJugador(id, carta);
    }

    public int getTipoCartaJugador(int id){
        return jugar.getTipoCartaJugador(id);
    }

    public void imprimirDptos(){
        System.out.println(jugar);
    }
}
