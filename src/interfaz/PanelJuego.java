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
    private JLabel texto3;
    private ArrayList<Boolean> test;

    private Risk risk;
    private Image fondoimg;
    private JTextArea msgbox;
    private Msgbox msg;
    private final String nuevalinea = "\n";

    private final Color amarillo = new Color(240, 240, 0);
    private final Color amarilloSelect = new Color(255, 217, 39);
    private final Color azul = new Color(0, 0, 255);
    private final Color azulSelect = new Color(26, 119, 255);
    private final Color rojo = new Color(225, 0, 0);
    private final Color rojoSelect = new Color(255, 74, 6);
    private final Color verde = new Color(0, 225, 0);
    private final Color verdeSelect = new Color(3, 255, 89);
    private final Color gris = new Color(97, 97, 97);
    private final Color blanco = new Color(255, 255, 255);

    private ArrayList<ImageIcon> dados;
    private ArrayList<BufferedImage> mapaimg;
    private ArrayList<JLabel> mapa;
    private ArrayList<ArrayList<Integer>> checkboxes;
    private ArrayList<JLabel> tropas;

    private boolean seleccionar;
    private int seleccionid;

    public PanelJuego(Risk risk) {
        //setPreferredSize(new Dimension(700, 600));
//        TitledBorder border = BorderFactory.createTitledBorder("Juego");
//        setBorder(border);
        
        dados = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            dados.add(new ImageIcon("res/dice" + (i+1) + ".png"));
        }
        
        setLayout(null);
        
//        texto1 = new JLabel("x");
//        texto1.setBounds(10,10, 100, 20);
//        add(texto1);
//
//        texto2 = new JLabel("y");
//        texto2.setBounds(100, 10, 100, 20);
//        add(texto2);
        
        texto3 = new JLabel("123");
        texto3.setBounds(0, 0, 400, 20);
        add(texto3);

        mapaimg = new ArrayList<>(32);
        mapa = new ArrayList<>(32);
        tropas = new ArrayList<>(32);

        try {
            fondoimg = ImageIO.read(new File("res/mapa/fondo.png"));
            //JLabel fondo = new JLabel(new ImageIcon(fondoimg));
            //fondo.setBounds(0, 0, 717, 670);
            //add(fondo);
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

            checkboxes = new ArrayList<>(557);
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

            in = new Scanner(new File("res/dptos_numpos.txt"));
            for (int i = 0; i < 32; i++) {
                int pos = in.nextInt();
                if(i != pos - 1) throw new IOException();
                int x = in.nextInt();
                int y = in.nextInt() - 5;
                JLabel tropa = new JLabel(risk.getTropasDpto(i) + "");
                tropa.setBounds(x, y, 20, 20);
                tropas.add(tropa);
                mapa.get(i).add(tropa);
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error", "Error al leer archivos del juego",
                    JOptionPane.WARNING_MESSAGE);
        }

        msg = new Msgbox();
        msg.setBounds(10, 530, 310, 128);
        add(msg);

        addMouseListener(this);

        this.risk = risk;
        test = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            test.add(true);
        }
        seleccionar = false;
        seleccionid = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoimg, 0, 0, null);
    }

    //datos de prueba en el metodo
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        //msg.print(String.valueOf(x) + " " + String.valueOf(y));
        for(ArrayList<Integer> check: checkboxes){
            if(x >= check.get(1) && x < check.get(3) && y >= check.get(2) && y < check.get(4)){
                seleccionarAtaque(check.get(0));

                //test
                if(test.get(check.get(0))) {
                    cambiarColor(check.get(0), verde);
                } else {
                        cambiarColor(check.get(0), verdeSelect);
                }
                test.set(check.get(0), !test.get(check.get(0)));
                break;
            }
        }

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


    }

    public static boolean isAlpha(BufferedImage image, int x, int y)
    {
        return (image.getRGB(x, y) & 0xFF000000) == 0xFF000000;
    }

    private void seleccionarAtaque(int dpto){
        if(seleccionar){
            AtacarTerritorio atacar = new AtacarTerritorio(seleccionid, dpto, 1, risk, dados);
            msg.print("El jugador " + 1 + " ha atacado a " + dpto + " usando " + seleccionid);
            seleccionar = false;
        } else {
            seleccionid = dpto;
            seleccionar = true;
            msg.print("El jugador " + 1 + " ha seleccionado " + dpto);
        }
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

    public void update(){
        for (int i = 0; i < 32; i++) {
            tropas.get(i).setText(risk.getTropasDpto(i) + "");
        }
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
