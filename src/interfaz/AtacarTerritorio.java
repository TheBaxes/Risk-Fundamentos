package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    Timer reportar;
    JButton boton;

    public AtacarTerritorio(int atk, int target, int jugador, Risk risk,
            ArrayList<ImageIcon> dados){
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
        setLayout(new GridLayout(2,3));
        add(new JLabel("Jugador: " + String.valueOf(this.jugador)));
        add(new JLabel("Atacante: " + String.valueOf(this.atk)));
        add(new JLabel("Blanco: " + String.valueOf(this.target)));
        
        
        dado1Img = new JLabel(dados.get(0));
        add(dado1Img);
        dado1 = 0;
        
        dado2Img = new JLabel(dados.get(0));
        add(dado2Img);
        dado2 = 0;
        
        contAnim = 0;
        ejecutar = new Timer(100, this);
        ejecutar.setActionCommand("ejecutar");
        esperar = new Timer(1000, this);
        esperar.setActionCommand("esperar");
        esperar.setRepeats(false);

        boton = new JButton("tirar");
        boton.addActionListener(this);
        boton.setActionCommand("tirar");
        add(boton);

        reportar = new Timer(1000, this);
        reportar.setActionCommand("reportar conquista");
        reportar.setRepeats(false);
        
        setVisible(true);
        
        this.risk = risk;

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
            if(!risk.comprobarVictoria()) new MoverTropas(atk, target, risk);
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
        if(risk.checkConquista(target, jugador)){
            reportarConquista();
        }
    }
    
    
}
