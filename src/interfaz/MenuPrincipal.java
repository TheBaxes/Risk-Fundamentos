/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Baxes
 */
public class MenuPrincipal extends JFrame {
    private PanelMenuP menu;
    public MenuPrincipal(){
        this.setTitle("Colombia Conquest");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        
        menu = new PanelMenuP(this);
        add(menu);
    }
    
    public static void main (String[] args){
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
    }
    
    public void salir(){
        this.dispose();
    }
    
    public void abrirJuego(int jugadores){
        Risk risk = new Risk(jugadores);
        this.setVisible(false);
    }
}
