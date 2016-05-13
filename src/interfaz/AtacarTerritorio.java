/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import sun.audio.*;
import risk.RiskException;

/**
 *
 * @author Baxes
 */
public class AtacarTerritorio extends JFrame implements ActionListener{
    private WindowListener close = new WindowAdapter(){
        public void windowClosing(WindowEvent evt) {
                timer.stop();
                dispose();
                if(risk.checkConquista(target, jugador)){
                    reportarConquista();
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
    private Timer timer;
    
    public AtacarTerritorio(int atk, int target, int jugador, Risk risk,
            ArrayList<ImageIcon> dados){
        setTitle("Atacando...");
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
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
        timer = new Timer(100, this);
        timer.setActionCommand("ejecutar");
        
        JButton boton = new JButton("tirar");
        boton.addActionListener(this);
        boton.setActionCommand("tirar");
        add(boton);
        
        setVisible(true);
        
        this.risk = risk;
        
        try{
            risk.comprobarAtaque(atk, target, jugador);
        } catch(RiskException e){
            dispose();
            JOptionPane.showMessageDialog(risk, e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
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
                atacar();
                timer.stop();
            }
        } else if(e.getActionCommand().equals("tirar")){
            tirarDados();
        }
    }
    
    private void tirarDados(){
        playDiceSound();
        timer.start();
    }
    
    private void reportarConquista(){
        this.dispose();
        JOptionPane.showMessageDialog(risk, "Territorio conquistado",
                "Felicidades", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void atacar(){
        risk.atacar(atk, target, jugador, dado1, dado2);
        if(risk.checkConquista(target, jugador)){
            reportarConquista();
        }
    }
    
    
}
