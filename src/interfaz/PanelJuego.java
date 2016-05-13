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
public class PanelJuego extends JPanel implements MouseListener{
    private JLabel texto1;
    private JLabel texto2;
    private AtacarTerritorio atacar;
    private Risk risk;
    private JLabel texto3;
    private ArrayList<ImageIcon> dados;
    
    public PanelJuego(Risk risk) {
        //setPreferredSize(new Dimension(700, 600));
        
        TitledBorder border = BorderFactory.createTitledBorder("Juego");
        setBorder(border);
        
        dados = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            dados.add(new ImageIcon("res/dice" + (i+1) + ".png"));
        }
        
        setLayout(new GridLayout(2, 2));
        
        texto1 = new JLabel("");
        add(texto1);
        
        texto2 = new JLabel("");
        add(texto2);
        
        texto3 = new JLabel("");
        add(texto3);
        ImageIcon icon = new ImageIcon("res/mindblow.gif", "test");
        add(new JLabel(icon));
        
        addMouseListener(this);
        
        this.risk = risk;
    }
    

    //datos de prueba en el metodo
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        texto1.setText(String.valueOf(x));
        texto2.setText(String.valueOf(y));
        int atk = 0;
        int target = 0;
        int jugador = 0;
        if(x > 390 && x < 600 &&
                y > 350 && y < 600){
            atk = 0;
            target = 1;
            jugador = 1;
            atacar = new AtacarTerritorio(atk, target, jugador, risk, dados);
            String atkJ = String.valueOf(risk.infoDpto(atk)[0]);
            String atkT = String.valueOf(risk.infoDpto(atk)[1]);
            String targetJ = String.valueOf(risk.infoDpto(target)[0]);
            String targetT = String.valueOf(risk.infoDpto(target)[1]);
            texto3.setText("Dpto0 Jugador:" + atkJ + " Tropas:" + atkT +
                     " | Dpto1 " + "Jugador:" + targetJ + " Tropas:" + targetT);
            
        }
        testUpdate();
    }
    
    public void testUpdate(){
        String atkJ = String.valueOf(risk.infoDpto(0)[0]);
        String atkT = String.valueOf(risk.infoDpto(0)[1]);
        String targetJ = String.valueOf(risk.infoDpto(1)[0]);
        String targetT = String.valueOf(risk.infoDpto(1)[1]);
        texto3.setText("Dpto0 Jugador:" + atkJ + " Tropas:" + atkT +
                 " | Dpto1 " + "Jugador:" + targetJ + " Tropas:" + targetT);
    }

    @Override
    public void mousePressed(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){
    }
    
    
}
