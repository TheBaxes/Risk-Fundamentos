/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Baxes
 */
public class PanelJuego extends JPanel implements MouseListener{
    private JLabel texto1;
    private JLabel texto2;
    private Risk risk;
    private JLabel texto3;
    private ArrayList<Boolean> test;

    private final Color amarillo = new Color(255, 255, 0);
    private final Color azul = new Color(0, 0, 255);
    private final Color rojo = new Color(225, 0, 0);
    private final Color verde = new Color(0, 225, 0);
    private final Color gris = new Color(97, 97, 97);
    private final Color blanco = new Color(255, 255, 255);

    private ArrayList<ImageIcon> dados;
    private ArrayList<BufferedImage> mapaimg;
    private ArrayList<JLabel> mapa;
    private ArrayList<ArrayList<Integer>> checkboxes;

    public PanelJuego(Risk risk) {
        //setPreferredSize(new Dimension(700, 600));
//        TitledBorder border = BorderFactory.createTitledBorder("Juego");
//        setBorder(border);
        
        dados = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            dados.add(new ImageIcon("res/dice" + (i+1) + ".png"));
        }
        
        setLayout(null);
        
        texto1 = new JLabel("x");
        texto1.setBounds(10,10, 100, 20);
        add(texto1);
        
        texto2 = new JLabel("y");
        texto2.setBounds(100, 10, 100, 20);
        add(texto2);
        
        texto3 = new JLabel("123");
        texto3.setBounds(10, 600, 400, 20);
        add(texto3);

        mapaimg = new ArrayList<>(32);
        mapa = new ArrayList<>(32);

        try {
            BufferedImage fondoimg = ImageIO.read(new File("res/mapa/fondo.png"));
            JLabel fondo = new JLabel(new ImageIcon(fondoimg));
            fondo.setBounds(0, 0, 717, 670);
            add(fondo);
            for (int i = 1; i <= 32; i++) {
                BufferedImage imagen;
                if(i < 10) {
                    imagen = ImageIO.read(new File("res/mapa/0" + i + ".png"));
                } else {
                    imagen = ImageIO.read(new File("res/mapa/" + i + ".png"));
                }
                mapaimg.add(imagen);
                JLabel dibujo = new JLabel(new ImageIcon(imagen));
                dibujo.setBounds(0, 0, 717, 670);
                mapa.add(dibujo);
                add(dibujo);
            }

            checkboxes = new ArrayList<>(500);
            Scanner in = new Scanner(new File("res/dptos_checkbox.txt"));
            String line;
            while(in.hasNext()){
                line = in.nextLine();
                Scanner lineRead = new Scanner(line);
                int pos = lineRead.nextInt() - 1;
                ArrayList<Integer> dptoCoordenadas = new ArrayList<>(5);
                dptoCoordenadas.add(pos);
                for(int i = 0; i < 4; i++) {
                    dptoCoordenadas.add(lineRead.nextInt());
                }
                checkboxes.add(dptoCoordenadas);
            }
        } catch(IOException e){
            System.out.print("error404");
        }
        
        addMouseListener(this);
        
        this.risk = risk;
        test = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            test.add(true);
        }
    }
    

    //datos de prueba en el metodo
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        for(ArrayList<Integer> check: checkboxes){
            if(x >= check.get(1) && x < check.get(3) && y >= check.get(2) && y < check.get(4)){
                if(test.get(check.get(0))) {
                    cambiarColor(check.get(0), gris);
                } else {
                        cambiarColor(check.get(0), blanco);
                }
                test.set(check.get(0), !test.get(check.get(0)));
                break;
            }
        }
        texto1.setText(String.valueOf(x));
        texto2.setText(String.valueOf(y));
        int atk = 0;
        int target = 0;
        int jugador = 0;
//        if(x > 390 && x < 600 &&
//                y > 400 && y < 600){
//            for (int i = 0; i < 32; i++) {
//                cambiarColor(i, azul);
//            }
//            atk = 0;
//            target = 1;
//            jugador = 1;
//            AtacarTerritorio atacar = new AtacarTerritorio(atk, target, jugador, risk, dados);
//            //risk.setEnabled(false);
//            String atkJ = String.valueOf(risk.getJugadorDpto(atk));
//            String atkT = String.valueOf(risk.getTropasDpto(atk));
//            String targetJ = String.valueOf(risk.getJugadorDpto(target));
//            String targetT = String.valueOf(risk.getTropasDpto(target));
//            texto3.setText("Dpto0 Jugador:" + atkJ + " Tropas:" + atkT +
//                     " | Dpto1 " + "Jugador:" + targetJ + " Tropas:" + targetT);
//
//        }
        testUpdate();


    }

    public static boolean isAlpha(BufferedImage image, int x, int y)
    {
        return (image.getRGB(x, y) & 0xFF000000) == 0xFF000000;
    }

    public void cambiarColor(int dpto, Color color){
        BufferedImage territorio = mapaimg.get(dpto);
        for(int y = 0; y < territorio.getHeight(); y++)
            for(int x = 0; x < territorio.getWidth(); x++)
            {
                if(isAlpha(territorio, x, y)) {

                    //mix imageColor and desired color
                    territorio.setRGB(x, y, color.getRGB());
                }
            }
        mapaimg.set(dpto, territorio);
        repaint();
    }

    public void testUpdate(){
        String atkJ = String.valueOf(risk.getJugadorDpto(0));
        String atkT = String.valueOf(risk.getTropasDpto(0));
        String targetJ = String.valueOf(risk.getJugadorDpto(1));
        String targetT = String.valueOf(risk.getTropasDpto(1));
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
