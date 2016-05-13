/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import risk.Jugar;
import risk.RiskException;

/**
 *
 * @author Baxes
 */
public class Risk extends JFrame{
    private PanelJugadores jugadores;
    private PanelJuego juego;
    private Jugar jugar;
    private int jugadorActual;
    
    public Risk(int numJugadores){
        this.setTitle("Colombia Conquest");
        this.setSize(1024,700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        
        juego = new PanelJuego(this);
        add(juego, BorderLayout.CENTER);
        jugadores = new PanelJugadores(numJugadores);
        add(jugadores, BorderLayout.LINE_END);
        
        setVisible(true);
        
        try{
            this.jugar = new Jugar(numJugadores);
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
        
        //Test commands
        jugar.setJugador(0, 1);
        jugar.setJugador(1, 2);
        for (int i = 0; i < 10; i++) {
            jugar.addTropas(0);
        }
        for (int i = 0; i < 10; i++) {
            jugar.addTropas(1);
        }
        testUpdate();
    }

    public void testUpdate(){
        juego.testUpdate();
    }
    
    public void comprobarAtaque(int atk, int target, int jugador)
            throws RiskException{
        jugar.comprobarAtaque(atk, target, jugador);
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        jugar.atacar(atk, target, jugador, dado1, dado2);
    }
    
    public boolean checkConquista(int target, int jugador){
        return jugar.checkConquista(target,jugador);
    }

    public int[] infoDpto(int idDpto){
        return jugar.getDpto(idDpto);
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
