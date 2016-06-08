/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase PanelMenuP
 * @author Baxes
 */
public class PanelMenuP extends JPanel implements ActionListener{
    private JButton dosJ;
    private JButton tresJ;
    private JButton cuatroJ;
    private JButton salir;
    private MenuPrincipal menu;

    /**
     * Crea el panel para el menu principal
     * @param menu Referencia de la clase MenuPrincipal
     */
    public PanelMenuP(MenuPrincipal menu){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //setMaximumSize(new Dimension(250, 350));
        
        JLabel a = new JLabel("Bienvenido a Conquest: Colombia", SwingConstants.CENTER);
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        add(a, c);
        JLabel b = new JLabel("Seleccionar cantidad de jugadores:", SwingConstants.CENTER);
        c.gridy = 1;
        c.insets = new Insets(0,0,40,0);
        c.ipady = 10;
        add(b, c);
        
        dosJ = new JButton("Dos Jugadores");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10,0,0,0);
        add(dosJ, c);
        dosJ.setActionCommand("dos");
        dosJ.addActionListener(this);
        //dosJ.setPreferredSize(new Dimension(50, 60));
        tresJ = new JButton("Tres Jugadores");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 3;
        add(tresJ, c);
        tresJ.setActionCommand("tres");
        tresJ.addActionListener(this);
        //tresJ.setPreferredSize(new Dimension(50, 60));
        cuatroJ = new JButton("Cuatro Jugadores");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 4;
        add(cuatroJ, c);
        cuatroJ.setActionCommand("cuatro");
        cuatroJ.addActionListener(this);
        //cuatroJ.setPreferredSize(new Dimension(50, 60));
        salir = new JButton("Salir");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 5;
        add(salir, c);
        salir.setActionCommand("salir");
        salir.addActionListener(this);
        //salir.setPreferredSize(new Dimension(50, 60));
        
        /*add(new JLabel("Bienvenido a Colombia Conquest", SwingConstants.CENTER));
        add(new JLabel("Seleccionar cantidad de jugadores:", SwingConstants.CENTER));
        add(dosJ);
        add(tresJ);
        add(cuatroJ);
        add(salir);*/
        
        this.menu = menu;
    }

    /**
     * Ejecuta una acción según el botón presionado en la interfaz
     * @param evento Evento
     */
    public void actionPerformed(ActionEvent evento){
        String comando = evento.getActionCommand();
        dosJ.setEnabled(false);
        tresJ.setEnabled(false);
        cuatroJ.setEnabled(false);
        switch(comando){
            case "dos":
                menu.abrirJuego(2);
                break;
            case "tres":
                menu.abrirJuego(3);
                break;
            case "cuatro":
                menu.abrirJuego(4);
                break;
            case "salir":
                menu.salir();
                break;
            default:
                break;
        }
    }
}
