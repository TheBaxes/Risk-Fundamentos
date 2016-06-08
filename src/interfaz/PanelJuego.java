/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Juego.RiskException;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase PanelJuego
 * @author Baxes
 */
public class PanelJuego extends JPanel implements MouseListener, MouseMotionListener{
    private ArrayList<Boolean> test;

    private Risk risk;
    private Image fondoimg;
    private Msgbox msg;

    private final Color amarillo = new Color(240, 240, 0);
    private final Color azul = new Color(8, 155, 255);
    private final Color rojo = new Color(212, 28, 28);
    private final Color verde = new Color(0, 225, 0);
    private final Color gris = new Color(97, 97, 97);
    private final Color select = new Color(255, 138, 0);
    private final Color blanco = new Color(255, 255, 255);

    private ArrayList<ImageIcon> dados;
    private ArrayList<BufferedImage> mapaimg;
    private ArrayList<JLabel> mapa;
    private ArrayList<ArrayList<Integer>> checkboxes;
    private ArrayList<JLabel> tropas;
    private ArrayList<BufferedImage> mapaSmall;

    private ArrayList<ImageIcon> faseimg;
    private JLabel fase;

    private JLabel nombreDpto;
    private JLabel regionDpto;

    private boolean seleccionar;
    private int seleccionid;
    private int faseActual;
    private int jugadorActual;

    /**
     * Crea el panel en el que se muestra el juego
     * @param risk Referencia a la clase Risk
     */
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

        mapaimg = new ArrayList<>(32);
        mapa = new ArrayList<>(32);
        tropas = new ArrayList<>(32);
        mapaSmall = new ArrayList<>(32);

        try {
            fondoimg = ImageIO.read(new File("res/mapa/fondo.png"));
            //JLabel fondo = new JLabel(new ImageIcon(fondoimg));
            //fondo.setBounds(0, 0, 717, 670);
            //add(fondo);
            for (int i = 1; i <= 32; i++) {
                BufferedImage imagen;
                BufferedImage imagen2;
                if(i < 10) {
                    imagen2 = ImageIO.read(new File("res/mapa/small0" + i + ".png"));
                    imagen = ImageIO.read(new File("res/mapa/0" + i + ".png"));
                } else {
                    imagen2 = ImageIO.read(new File("res/mapa/small" + i + ".png"));
                    imagen = ImageIO.read(new File("res/mapa/" + i + ".png"));
                }
                mapaimg.add(imagen);
                mapaSmall.add(imagen2);
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

            fase = new JLabel();
            faseimg = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                faseimg.add(new ImageIcon("res/mapa/fase" + i + ".png"));
            }
            fase.setIcon(faseimg.get(0));
            fase.setBounds(456, 142, 200, 33);
            add(fase);
        } catch(IOException e){
            JOptionPane.showMessageDialog(risk, "Error", "Error al leer archivos del juego",
                    JOptionPane.WARNING_MESSAGE);
        }

        msg = new Msgbox();
        msg.setBounds(10, 530, 310, 128);
        add(msg);

        nombreDpto = new JLabel("");
        nombreDpto.setHorizontalAlignment(SwingConstants.CENTER);
        nombreDpto.setBounds(555, 519, 139, 37);
        add(nombreDpto);

        regionDpto = new JLabel("");
        regionDpto.setFont(new Font("Arial", Font.PLAIN, 14));
        regionDpto.setHorizontalAlignment(SwingConstants.CENTER);
        regionDpto.setBounds(555, 559, 139, 37);
        add(regionDpto);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.risk = risk;
        risk.setMsgbox(msg);
        test = new ArrayList<>(32);
        for (int i = 0; i < 32; i++) {
            test.add(true);
        }
        seleccionar = false;
        seleccionid = -1;
        faseActual = 0;
        jugadorActual = 0;
    }

    /**
     * Método que pinta las regiones en la ventana de juego
     */
    public void mostrarRegiones(){
        for (int i = 0; i < 32; i++) {
            tropas.get(i).setText("");
            switch (risk.getRegionDpto(i)){
                case "Insular":
                    cambiarColor(i, Color.magenta);
                    break;
                case "Caribe":
                    cambiarColor(i, amarillo);
                    break;
                case "Andina":
                    cambiarColor(i, azul);
                    break;
                case "Pacífica":
                    cambiarColor(i, rojo);
                    break;
                case "Orinoquía":
                    cambiarColor(i, verde);
                    break;
                case "Amazonia":
                    cambiarColor(i, Color.orange);
                    break;
            }
        }
    }

    /**
     * Método que selecciona un territorio y ejecuta una acción dependiendo de la fase correspondiente
     * @param dpto Departamento a seleccionar
     * @param jugador Jugador que ejecuta la acción
     * @param fase Fase
     */
    public void seleccionar(int dpto, int jugador, int fase){
        if(seleccionid == dpto && fase > 1) {
            seleccionar = false;
            seleccionid = -1;
            cambiarColorClick(dpto, false);
            msg.print("El jugador " + (jugador + 1) + " ha deseleccionado " + risk.getNombreDpto(dpto));
            return;
        }
        if (seleccionar) {
            switch (fase) {
                case 0:
                    risk.seleccionarTerritorio(dpto);
                    break;
                case 1:
                    try {
                        if(risk.getTropasJugador(jugador) > 0) {
                            risk.addTropasDpto(dpto, 1);
                            risk.reduceTropasJugador(jugador, 1);
                            msg.print("El jugador " + (jugador + 1) + " ha agregado 1 tropa a " +
                                    risk.getNombreDpto(dpto));
                            if(risk.getTropasJugador(jugador) == 0){
                                risk.actionPerformed(new ActionEvent(this, 0, "siguiente"));
                            }
                        } else {
                            JOptionPane.showMessageDialog(risk, "El jugador " + (jugador + 1)  + " no tiene más" +
                                    " tropas para ubicar", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (RiskException ex) {
                        JOptionPane.showMessageDialog(risk, ex.getMessage(), "Aviso",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case 2:
                    cambiarColorClick(dpto, true);
                    new AtacarTerritorio(seleccionid, dpto, jugador, risk, dados, mapaSmall);
                    msg.print("El jugador " + (jugador + 1) + " ha seleccionado " + risk.getNombreDpto(dpto));
                    break;
                case 3:
                    cambiarColorClick(dpto, true);
                    try {
                        risk.comprobarTransporte(seleccionid, dpto, jugador);
                        new MoverTropas(seleccionid, dpto, risk, false);
                    } catch (RiskException e){
                        JOptionPane.showMessageDialog(risk, e.getMessage(),
                                "Aviso", JOptionPane.WARNING_MESSAGE);
                        risk.update(seleccionid, dpto, false);
                        risk.print("No se pudieron mover las tropas porque " + e.getMessage());
                    }
                    break;
            }
            seleccionar = false;
            seleccionid = -1;
        } else {
            if(!risk.checkTerritorio(dpto, jugador) && fase > 0){
                msg.print("El territorio seleccionado no pertenece al jugador");
                return;
            }
            if(risk.getTropasDpto(dpto) <= 1 && fase == 2){
                msg.print("El territorio seleccionado no tiene suficientes tropas para atacar");
                return;
            }
            seleccionid = dpto;
            seleccionar = true;
            if(fase <= 1) seleccionar(dpto, jugador, fase);
            else {
                msg.print("El jugador " + (jugador + 1) + " ha seleccionado " + risk.getNombreDpto(dpto));
                cambiarColorClick(dpto, true);
            }
        }
    }

    /**
     * Método que cambia el color de un territorio
     * @param dpto Departamento
     * @param color Color
     */
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

    /**
     * Método que cambia el color de un territorio por el de su estado de selección
     * @param dpto Departamento
     * @param clickeado Estado del departamento
     */
    public void cambiarColorClick(int dpto, boolean clickeado){
        if (clickeado) cambiarColor(dpto, select);
        else cambiarColor(dpto, getColorJugador(risk.getJugadorDpto(dpto)));
    }

    /**
     * Método que retorna el color respectivo a cada jugador
     * @param jugador Jugador
     * @return Objeto color
     */
    public Color getColorJugador(int jugador){
        switch (jugador) {
            case 0:
                return amarillo;
            case 1:
                return azul;
            case 2:
                return rojo;
            case 3:
                return verde;
            case 4:
                return gris;
        }
        return blanco;
    }

    /**
     * Método que actualiza la fase actual en la clase
     * @param fase Fase
     */
    public void cambiarFase(int fase){
        this.fase.setIcon(faseimg.get(fase));
        faseActual = fase;
    }

    /**
     * Método que cambia el jugador actual en la clase
     * @param jugador Jugador
     */
    public void cambiarJugador(int jugador){
        jugadorActual = jugador;
        risk.print("Turno del jugador " + (jugador + 1));
    }

    /**
     * Método que comprueba que territorio se encuentra en las coordenadas ingresadas.
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return Retorna el id del departamento clickeado. En caso de que no halla un territorio en las coordenadas
     * se retorna -1
     */
    public int checkDpto(int x, int y){
        for(ArrayList<Integer> check: checkboxes){
            if(x >= check.get(1) && x < check.get(3) && y >= check.get(2) && y < check.get(4)){
                return check.get(0);
            }
        }
        return -1;
    }

    /**
     * Método que actualiza la interfaz (Genera mucho lag)
     */
    public void update(){
        for (int i = 0; i < 32; i++) {
            tropas.get(i).setText(risk.getTropasDpto(i) + "");
            cambiarColor(i, getColorJugador(risk.getJugadorDpto(i)));
        }
    }

    /**
     * Método que actualiza la cantidad de tropas de un territorio
     * @param dpto Departamento
     */
    public void updateTropas(int dpto){
        tropas.get(dpto).setText(risk.getTropasDpto(dpto) + "");
    }

    /**
     * Método que actualiza un departamento
     * @param dpto Departamento
     */
    public void update(int dpto){
        tropas.get(dpto).setText(risk.getTropasDpto(dpto) + "");
        cambiarColor(dpto, getColorJugador(risk.getJugadorDpto(dpto)));
    }

    /**
     * Método que actualiza las tropas de dos departamentos
     * @param dptoA Departamento 1
     * @param dptoB Departamento 2
     */
    public void update(int dptoA, int dptoB){
        tropas.get(dptoA).setText(risk.getTropasDpto(dptoA) + "");
        tropas.get(dptoB).setText(risk.getTropasDpto(dptoB) + "");
    }

    /**
     * Método que actualiza las tropas y el estado de dos departamentos
     * @param dptoA Departamento 1
     * @param dptoB Departamento 2
     * @param clickeado Estado de los departamentos
     */
    public void update(int dptoA, int dptoB, boolean clickeado){
        tropas.get(dptoA).setText(risk.getTropasDpto(dptoA) + "");
        tropas.get(dptoB).setText(risk.getTropasDpto(dptoB) + "");
        cambiarColorClick(dptoA, clickeado);
        cambiarColorClick(dptoB, clickeado);
    }

    /**
     * Método que comprueba la ubicación del click y ejecuta el método seleccionar si se clickeo un territorio
     * @param e Evento
     */
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        int dpto = checkDpto(x, y);
        if(dpto > -1) seleccionar(dpto, jugadorActual, faseActual);
    }

    /**
     * Método que comprueba si un pixel no es transparente en una imagen
     * @param image Imagen
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return booleano con el resultado de la condición
     */
    public static boolean isAlpha(BufferedImage image, int x, int y){
        return (image.getRGB(x, y) & 0xFF000000) == 0xFF000000;
    }

    /**
     * Método que pinta el fondo
     * @param g g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoimg, 0, 0, null);
    }

    /**
     * Método que muestra los datos del territorio en el que se encuentra el mouse
     * @param e Evento
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //msg.print(String.valueOf(x) + " " + String.valueOf(y));
        int dpto = checkDpto(x, y);
        if(dpto > -1){
            String nombre = risk.getNombreDpto(dpto);
            String region = risk.getRegionDpto(dpto);
            if(nombre.length() > 20) nombreDpto.setFont(new Font("Arial", Font.PLAIN, 12));
            else nombreDpto.setFont(new Font("Arial", Font.PLAIN, 14));
            nombreDpto.setText(nombre);
            regionDpto.setText(region);
        } else {
            nombreDpto.setText("");
            regionDpto.setText("");
        }
    }

    /**
     * Método sin usar
     * @param e e
     */
    @Override
    public void mousePressed(MouseEvent e){
    }

    /**
     * Método sin usar
     * @param e e
     */
    @Override
    public void mouseReleased(MouseEvent e){
    }

    /**
     * Método sin usar
     * @param e e
     */
    @Override
    public void mouseEntered(MouseEvent e){
    }

    /**
     * Método sin usar
     * @param e e
     */
    @Override
    public void mouseExited(MouseEvent e){
    }

    /**
     * Método sin usar
     * @param e e
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
