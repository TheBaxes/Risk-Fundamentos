/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author Baxes
 */
public class PanelCartas extends JPanel{
    private boolean[] cartasclick;
    private ArrayList<JLabel> cartas;
    private ImageIcon carta = new ImageIcon("res/escudo.png","Escudo de Colombia - carta");
    private ImageIcon cartaClick = new ImageIcon("res/escudoClick.png", "Clickeado");
    
    public PanelCartas(){
//        TitledBorder border = new TitledBorder("Cartas:");
//        setBorder(border);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 0, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.000000001;
        //test
        cartasclick = new boolean[5];
        cartas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel boton = new JLabel(carta);
            boton.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    int k = cartas.indexOf(boton);
                    change(k);
                }
            });
            cartas.add(boton);
            add(boton, c);
            c.weightx *= 50;
        }
    }
    
    public void change(int id){
        if(cartasclick[id]){
            cartasclick[id] = false;
            cartas.get(id).setIcon(carta);
        } else {
            cartasclick[id] = true;
            cartas.get(id).setIcon(cartaClick);
        }
        System.out.println("Carta nº" + id + " es " + cartasclick[id]);
    }
    
    public void paintComponent(Graphics page){
        
    }
}
