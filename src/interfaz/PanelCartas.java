/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Clase PanelCartas
 * @author Baxes
 */
public class PanelCartas extends JPanel{
    private boolean[] cartasclick;
    private ArrayList<JLabel> cartas;
//    private ImageIcon carta = new ImageIcon("res/escudo.png","Escudo de Colombia - carta");
//    private ImageIcon cartaClick = new ImageIcon("res/escudoClick.png", "Clickeado");
    private ArrayList<ImageIcon> cartaImg;
    private ArrayList<ImageIcon> cartaClick;
    private int numJugador;
    private Risk risk;
    GridBagConstraints c;

    /**
     * Crea un nuevo panel de cartas
     * @param numJugador Número del jugador al que pertenece
     * @param risk Referencia a la clase Risk
     */
    public PanelCartas(int numJugador, Risk risk){
//        TitledBorder border = new TitledBorder("Cartas:");
//        setBorder(border);
        
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(0, 5, 0, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.000000001;
        //test
        cartasclick = new boolean[5];
        cartas = new ArrayList<>(5);
//        for (int i = 0; i < 5; i++) {
//            JLabel boton = new JLabel(carta);
//            boton.addMouseListener(new MouseAdapter(){
//                public void mouseClicked(MouseEvent e) {
//                    int k = cartas.indexOf(boton);
//                    click(k);
//                }
////            });
//            cartas.add(boton);
//            add(boton, c);
//            c.weightx *= 50;
//        }

        cartaImg = new ArrayList<>(4);
        cartaClick = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            cartaImg.add(new ImageIcon("res/carta" + i + ".png"));
            cartaClick.add(new ImageIcon("res/cartasel" + i + ".png"));
        }

        this.numJugador = numJugador;
        this.risk = risk;
    }

    /**
     * Agrega una carta nueva en la interfaz
     * @param carta Carta a agregar
     */
    public void agregarCarta(int carta){
        JLabel boton = new JLabel(cartaImg.get(carta));
        boton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                int k = cartas.indexOf(boton);
                click(k);
            }
        });
        cartas.add(boton);
        add(boton, c);
        c.weightx *= 50;
        risk.repaint();
        risk.updateTropas(numJugador);
    }

    /**
     * Actualiza el panel en la interfaz
     */
    public void update(){
        c.weightx /= (int)Math.pow(50.0,(double)cartas.size() + 1.0);
        removeAll();
        cartas.clear();
        cartasclick = new boolean[5];
        ArrayList<Integer> updateCartas = risk.getCartasJugador(numJugador);
        for (Integer updateCarta : updateCartas) {
            agregarCarta(updateCarta);
        }
        risk.repaint();
        risk.updateTropas(numJugador);
    }

    /**
     * Método que selecciona una carta al hacer click en ella
     * @param id Posición de la carta
     */
    public void click(int id){
        if(cartasclick[id]){
            cartasclick[id] = false;
            cartas.get(id).setIcon(cartaImg.get(risk.getCartasJugador(numJugador).get(id)));
            risk.deseleccionarCarta(id, numJugador);
        } else {
            cartasclick[id] = true;
            cartas.get(id).setIcon(cartaClick.get(risk.getCartasJugador(numJugador).get(id)));
            int condicion = risk.seleccionarCarta(id, numJugador);
            if(condicion == 3) {
                if(risk.checkContCartas(numJugador)){
                    risk.print("El jugador " + (numJugador + 1) + " ha usado 3 cartas y ha obtenido 7 tropas extras");
                    update();
                }
                for (int i = 0; i < 5; i++) {
                    if (cartasclick[i]){
                        click(i);
                    }
                }
            }
        }
        risk.repaint();
    }

    /**
     * Método que deja el fondo transparente
     * @param page page
     */
    public void paintComponent(Graphics page){

    }
}
