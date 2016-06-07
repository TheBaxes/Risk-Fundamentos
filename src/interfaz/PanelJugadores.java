/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 *
 * @author Baxes
 */
public class PanelJugadores extends JPanel{
    private ArrayList<PanelJugador> jugadores;
    private Image img;

    
    public PanelJugadores(int numJugadores, Risk risk) {
        setPreferredSize(new Dimension(300, 0));
        
        //img = new ImageIcon("res/paper background.png").getImage();
        
        TitledBorder border = BorderFactory.createTitledBorder("Jugadores");
        setBorder(border);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        jugadores = new ArrayList<PanelJugador>(numJugadores);
        c.insets = new Insets(5, 0, 8, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.0000001;
        c.anchor = GridBagConstraints.PAGE_START;
        for (int i = 0; i < numJugadores; i++) {
            c.gridy = i;
            PanelJugador jugador = new PanelJugador(i, risk);
            /*
            switch(i) {
            case 0:
            jugador.background(Color.red);
            break;
            case 1:
            jugador.background(Color.cyan);
            break;
            case 2:
            jugador.background(Color.green);
            break;
            case 3:
            jugador.background(Color.orange);
            }
            */
            jugadores.add(jugador);
            add(jugadores.get(i), c);
            //c.anchor = GridBagConstraints.CENTER;
            c.weighty *= 100;
        }
    }

    public void agregarCarta(int jugador, int carta){
        jugadores.get(jugador).agregarCarta(carta);
    }

    public void updateTropas(int jugador){
        jugadores.get(jugador).updateTropas();
    }
    
//    public void paintComponent(Graphics page){
//        page.drawImage(img, 0, 0, null);
//    }
    
}
