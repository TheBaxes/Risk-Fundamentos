package Interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.io.*;
import sun.audio.*;
import Juego.RiskException;

/**
 *
 * @author Baxes
 */
public class AtacarTerritorio extends JFrame implements ActionListener{
    private WindowListener close = new WindowAdapter(){
        public void windowClosing(WindowEvent evt) {
                risk.update(atk, target, false);
                ejecutar.stop();
                esperar.stop();
                dispose();
                risk.setEnabled(true);
                risk.requestFocus();
                if(risk.checkConquista(target, jugador)){
                    reportarConquista();
                } else {
                    risk.print("El jugador " + (jugador+1) + " ha cancelado el ataque");
                }
            }
    };
    private boolean cancelar;
    private int jugador;
    private int atk;
    private int target;
    private Risk risk;
    private JLabel dado1Img;
    private JLabel dado2Img;
    private ArrayList<ImageIcon> dados;
    private int dado1;
    private int dado2;
    private int contAnim;
    private Timer ejecutar;
    private Timer esperar;
    private Timer reportar;

    private JLabel nombreA;
    private JLabel nombreB;
    private JLabel tropasA;
    private JLabel tropasB;
    private JLabel dptoA;
    private JLabel dptoB;
    private JButton boton;
    private ArrayList<BufferedImage> territorios;

    private final Color amarillo = new Color(240, 240, 0);
    private final Color azul = new Color(8, 155, 255);
    private final Color rojo = new Color(212, 28, 28);
    private final Color verde = new Color(0, 225, 0);
    private final Color gris = new Color(97, 97, 97);

    public AtacarTerritorio(int atk, int target, int jugador, Risk risk,
            ArrayList<ImageIcon> dados, ArrayList<BufferedImage> territorios){
        setContentPane(new JLabel(new ImageIcon("res/fondoatk.png")));
        setTitle("Atacando...");
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(risk);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(close);
        cancelar = false;
        this.jugador = jugador;
        this.atk = atk;
        this.target = target;
        this.dados = dados;
        setLayout(null);
        add(new JLabel("Jugador: " + String.valueOf(this.jugador)));
        add(new JLabel("Atacante: " + String.valueOf(this.atk)));
        add(new JLabel("Blanco: " + String.valueOf(this.target)));
        
        nombreA = new JLabel(risk.getNombreDpto(atk));
        nombreA.setHorizontalAlignment(SwingConstants.CENTER);
        if(atk == 0) nombreA.setFont(new Font("Arial", Font.PLAIN, 12));
        nombreA.setBounds(13, 5, 100, 17);
        add(nombreA);

        nombreB = new JLabel(risk.getNombreDpto(target));
        nombreB.setHorizontalAlignment(SwingConstants.CENTER);
        nombreB.setBounds(179, 5, 100, 17);
        add(nombreB);

        this.territorios = territorios;
        this.risk = risk;

        dptoA = new JLabel(new ImageIcon(territorios.get(atk)));
        cambiarColor(atk);
        dptoA.setBounds(13, 30, 100, 100);
        add(dptoA);

        dptoB = new JLabel(new ImageIcon(territorios.get(target)));
        cambiarColor(target);
        dptoB.setBounds(179, 30, 100, 100);
        add(dptoB);

        tropasA = new JLabel(risk.getTropasDpto(atk) + "");
        tropasA.setBounds(35, 130, 30, 30);
        add(tropasA);

        tropasB = new JLabel(risk.getTropasDpto(target) + "");
        tropasB.setBounds(245, 140, 20, 20);
        add(tropasB);

        dado1Img = new JLabel(dados.get(0));
        dado1Img.setBounds(70, 130, 32, 32);
        add(dado1Img);
        dado1 = 0;
        
        dado2Img = new JLabel(dados.get(0));
        dado2Img.setBounds(188, 130, 32, 32);
        add(dado2Img);
        dado2 = 0;

        boton = new JButton("GO");
        boton.addActionListener(this);
        boton.setActionCommand("tirar");
        boton.setBounds(125, 110, 40, 30);
        add(boton);

        contAnim = 0;
        ejecutar = new Timer(100, this);
        ejecutar.setActionCommand("ejecutar");
        esperar = new Timer(1000, this);
        esperar.setActionCommand("esperar");
        esperar.setRepeats(false);

        reportar = new Timer(1000, this);
        reportar.setActionCommand("reportar conquista");
        reportar.setRepeats(false);
        
        setVisible(true);

        risk.setEnabled(false);
        if(comprobarAtaque()) risk.print("El jugador " + (jugador+1) + " ha atacado " + risk.getNombreDpto(target)
                + " usando " + risk.getNombreDpto(atk));

    }

    private boolean comprobarAtaque(){
        try{
            risk.comprobarAtaque(atk, target, jugador);
            return true;
        } catch(RiskException e){
            risk.setEnabled(true);
            dispose();
            risk.requestFocus();
            JOptionPane.showMessageDialog(risk, e.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            risk.update(atk, target, false);
            risk.print("No se pudo ejecutar el ataque porque " + e.getMessage());
        }
        return false;
    }

    private void playDiceSound(){
        try {
        InputStream in = new FileInputStream("res/dice_sound.wav");
            try {
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);
            
            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
            } catch(IOException e) {
                //Mostrar error
            }
        } catch(FileNotFoundException e) {
            //Mostrar error
        }
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("ejecutar")) {
            dado1 = (int)(Math.random()*6 + 1);
            dado2 = (int)(Math.random()*6 + 1);
            dado1Img.setIcon(dados.get(dado1 - 1));
            dado2Img.setIcon(dados.get(dado2 - 1));
            contAnim++;
            if(contAnim == 10) {
                contAnim = 0;
                ejecutar.stop();
                esperar.start();
                atacar();
                risk.update(atk, target);
            }
        } else if(e.getActionCommand().equals("tirar")){
            boton.setEnabled(false);
            tirarDados();
        } else if (e.getActionCommand().equals("esperar")) {
            tirarDados();
        } else if (e.getActionCommand().equals("reportar conquista")){
            risk.update(atk, target);
            risk.setEnabled(true);
            risk.requestFocus();
            risk.print("El jugador " + (jugador+1) + " ha conquistado " + risk.getNombreDpto(target));
            JOptionPane.showMessageDialog(risk, "Territorio conquistado",
                    "Felicidades", JOptionPane.INFORMATION_MESSAGE);
            if(!risk.comprobarVictoria()) new MoverTropas(atk, target, risk, true);
            this.dispose();
        }
    }
    
    private void tirarDados(){
        if(comprobarAtaque()) {
            playDiceSound();
            ejecutar.start();
        }
    }
    
    private void reportarConquista(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        esperar.stop();
        reportar.start();
    }
    
    private void atacar(){
        risk.atacar(atk, target, jugador, dado1, dado2);
        tropasA.setText(risk.getTropasDpto(atk) + "");
        tropasB.setText(risk.getTropasDpto(target) + "");
        if(risk.checkConquista(target, jugador)){
            reportarConquista();
        }
    }

    public void cambiarColor(int dpto){
        Color color = getColorJugador(risk.getJugadorDpto(dpto));
        BufferedImage territorio = this.territorios.get(dpto);
        ColorModel cm = territorio.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = territorio.copyData(null);
        BufferedImage copiaTerritorio = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        //new BufferedImage(territorio.getWidth(), territorio.getHeight(), territorio.getType());
        for(int y = 0; y < territorio.getHeight(); y++)
            for(int x = 0; x < territorio.getWidth(); x++)
            {
                if(isAlpha(territorio, x, y)) {

                    //mix imageColor and desired color
                    territorio.setRGB(x, y, color.getRGB());
                }
            }
        territorios.set(dpto, territorio);
        repaint();
        territorios.set(dpto, copiaTerritorio);
    }

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
        }
        return gris;
    }

    public static boolean isAlpha(BufferedImage image, int x, int y){
        return image.getRGB(x, y) == Color.BLACK.getRGB();
    }
}
