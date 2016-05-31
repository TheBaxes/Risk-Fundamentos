/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 *
 * @author Baxes
 */
public class PanelJugador extends JPanel{
    private JLabel tropas;
    private PanelCartas cartas;
    private int idJugador;
    private Image img;
    private Risk risk;
    
    public PanelJugador(int numJugador){
        idJugador = numJugador;
        setPreferredSize(new Dimension(280, 120));
        
        img = new ImageIcon("res/back.png").getImage();
        
        //TitledBorder border = new 
        //LineBorder border = new LineBorder(Color.yellow, 2, true);
//        Border thatBorder1 = new LineBorder(Color.black, 5, true);
//        Border border = new TitledBorder(thatBorder1, "Harder");
//        Font font = new Font("Serif", Font.ITALIC, 12);
//        TitledBorder border = new TitledBorder(thatBorder2, "Harder", TitledBorder.LEFT,
//        TitledBorder.ABOVE_BOTTOM, font, Color.RED);
    
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(Color.yellow, 0), "Jugador "+numJugador);
        setBorder(border);
        
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 1.0;
        tropas = new JLabel("Tropas disponibles: 0");
        add(tropas, c);
        
        c.insets = new Insets(10,15,10,20);
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weightx = 0.000001;
        cartas = new PanelCartas(numJugador);
        add(cartas, c);
    }

    public void updateInfo(int num){
        tropas.setText("Tipo carta: " + num);
    }
    
//    public void background(Color color){
//        tropas.setBackground(color);
//                setBackground(color);
//    }
    
    public void paintComponent(Graphics page){
        page.drawImage(img, 0, 0, null);
    }
}
