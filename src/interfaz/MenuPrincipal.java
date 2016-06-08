/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Clase MenuPrincipal
 * @author Sebastián Patiño Barrientos
 */
public class MenuPrincipal extends JFrame {
    private PanelMenuP menu;

    /**
     * Crea una nueva instancia del menú principal
     */
    public MenuPrincipal(){
        this.setTitle("Conquest: Colombia");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        
        menu = new PanelMenuP(this);
        add(menu);
    }

    /**
     * Método Main
     * @param args Argumentos (No hay)
     */
    public static void main (String[] args){
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
    }

    /**
     * Método que cierra la ventana
     */
    public void salir(){
        this.dispose();
    }

    /**
     * Método que abre una partida nueva
     * @param jugadores Cantidad de jugadores
     */
    public void abrirJuego(int jugadores){
        this.setVisible(false);
        Risk risk = new Risk(jugadores);
        this.dispose();
    }
}
