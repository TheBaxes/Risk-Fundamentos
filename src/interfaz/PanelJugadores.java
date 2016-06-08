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
    private JButton siguiente;

    
    public PanelJugadores(int numJugadores, Risk risk) {
        setPreferredSize(new Dimension(300, 0));
        
        img = new ImageIcon("res/fondojugadores.png").getImage();
        
        //TitledBorder border = BorderFactory.createTitledBorder("Jugadores");
        //setBorder(border);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        jugadores = new ArrayList<>(numJugadores);
        c.insets = new Insets(25, 13, 8, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.0000001;
        c.anchor = GridBagConstraints.PAGE_START;
        for (int i = 0; i < numJugadores; i++) {
            c.gridy = i;
            PanelJugador jugador = new PanelJugador(i, risk);
            jugadores.add(jugador);
            add(jugador, c);
            //c.anchor = GridBagConstraints.CENTER;
            c.weighty *= 100;
            c.insets = new Insets(5, 13, 8, 0);
        }

        siguiente = new JButton("Siguiente jugador");
        siguiente.setEnabled(false);
        siguiente.addActionListener(risk);
        siguiente.setActionCommand("siguiente");
        GridBagConstraints d = new GridBagConstraints();
        d.gridy = 4;
        d.fill = GridBagConstraints.NONE;
        d.insets = new Insets(10, 8, 20, 0);
        add(siguiente, d);
    }

    public void cambiarBoton(String texto){
        siguiente.setText(texto);
        siguiente.setEnabled(true);
    }

    public void removerJugador(int jugador){
        getComponent(jugador).setVisible(false);
    }

    public void agregarCarta(int jugador, int carta){
        jugadores.get(jugador).agregarCarta(carta);
    }

    public void updateTropas(int jugador){
        jugadores.get(jugador).updateTropas();
    }
    
    public void paintComponent(Graphics page){
        page.drawImage(img, 0, 0, null);
    }
    
}
