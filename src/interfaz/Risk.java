/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Juego.Juego;
import Juego.RiskException;

/**
 *
 * @author Baxes
 */
public class Risk extends JFrame implements ActionListener{
    private PanelJugadores jugadores;
    private PanelJuego juego;
    private Juego jugar;
    private int jugadorActual;
    private Msgbox msg;
    private int numJugadores;
    private boolean preinicio;
    private boolean[] jugadoresRemovidos;
    
    public Risk(int numJugadores){
        this.setTitle("Colombia Conquest");
        this.setSize(1024,700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try{
            this.jugar = new Juego(numJugadores, this);
        } catch(FileNotFoundException e) {
            dispose();
            JOptionPane.showMessageDialog(this, "dptos_adyacencia.txt no "
                    + "existe en la carpeta del juego.", "Error al leer "
                    + "archivos", JOptionPane.WARNING_MESSAGE);
        } catch(RiskException e) {
            dispose();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);
        } catch(IOException e){
            dispose();
            JOptionPane.showMessageDialog(this, "Error al leer archivos del juego",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }

        setLayout(new BorderLayout());

        juego = new PanelJuego(this);
        add(juego, BorderLayout.CENTER);
        jugadores = new PanelJugadores(numJugadores, this);
        add(jugadores, BorderLayout.LINE_END);

        this.numJugadores = numJugadores;
        preinicio = true;
        jugadoresRemovidos = new boolean[numJugadores];
        
        setVisible(true);

        faseSeleccion();

        //juego.mostrarRegiones();
    }

    public void faseSeleccion(){
        Object[] mensaje = {
                "Bienvenidos a Conquest: Colombia.",
                "Para comenzar el juego cada jugador deberá escoger sus territorios iniciales haciendo click en el",
                "departamento que cada deseen en su respectivo turno. Se podrá seleccionar uno en cada turno.",
                "Una vez todos los territorios estén ocupados, se iniciará la partida.",
                " ",
                "Nota:",
                "En caso de que sean dos jugadores, 10 territorios serán seleccionados de manera aleatoria después",
                "del turno del segundo jugador. En caso de que sean 3 jugadores, 2 territorios serán seleccionados",
                "al azar antes de comenzar la selección de territorios."
        };
        JOptionPane.showMessageDialog(this, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
        if(numJugadores == 3){
            jugar.randomEmptyDpto();
            jugar.randomEmptyDpto();
        }
    }

    public boolean seleccionarTerritorio(int dpto){
        if(!jugar.seleccionTerritorio(dpto)) {
            JOptionPane.showMessageDialog(this, "El territorio ya está ocupado", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(cambiarFase() > 0){
            Object[] mensaje = {
                    "Ahora que todos han escogido territorios, es momento de comenzar a jugar.",
                    "El turno de cada jugador se compone de 3 fases: Refuerzo, Ataque y Transporte.",
                    " ",
                    "Refuerzo: ",
                    "En la etapa de Refuerzo cada jugador recibe tropas en su turno en base a los territorios",
                    "que tenga, las regiones completas que domine y las tropas extras que obtenga por usar",
                    "cartas. Cada turno recibes una carta nueva (para un máximo de 5) y al juntar y seleccionar",
                    "con el click 3 del mismo tipo o 3 totalmente distintas recibes 7 tropas adicionales para",
                    "usar cuando quieras. Además, las tropas que no uses en un turno se guardan para que las",
                    "uses después. Usa click izquierdo para adicionar una tropa a un territorio y click derecho",
                    "para retirar una tropa. Para acabar esta fase presiona el botón que dice Siguiente fase."
            };
            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            mensaje = new Object[]{
                    "Ataque: ",
                    "En la etapa de Ataque el jugador activo podrá usar sus territorios para invadir y conquistar",
                    "otros departamentos. Para poder ejecutar un ataque es necesario que el territorio que",
                    "selecciones sea tuyo y que tenga más de 1 tropa en él. Los ataques se ejecutarán mediante",
                    "tiradas de dados, en las cuales si el dado de la izquierda (el atacante) es mayor que el",
                    "dado de la derecha (el objetivo) el territorio objetivo perderá una tropa. En caso contrario",
                    "tu territorio perderá una tropa. Los dados se tirarán hasta que el territorio atacante se",
                    "quede sin tropas suficientes, el territorio objetivo pierda todas sus tropas o el jugador",
                    "atacante cancele el ataque cerrando la ventana de ataque.",
                    "Para acabar esta fase presiona el botón que dice Siguiente fase."
            };
            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            mensaje = new Object[]{
                    "Transporte: ",
                    "En la etapa de Transporte el jugador activo tiene la posibilidad de mover tropas entre",
                    "dos territorios que él posea, sin importar que tan lejos estén. Solo se necesita darle",
                    "click al territorio de origen de primero y al territorio que recibirá las tropas después.",
                    "Esta fase es opcional y se puede saltar presionando el botón que dice Terminar turno."
            };
            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            mensaje = new Object[]{
                    "Para comenzar cada jugador recibirá 10 tropas.",
                    "Ubíquenlas en los territorios que deseen y que comience el juego."
            };
            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            jugadores.cambiarBoton("Siguiente jugador");
            for (int i = 0; i < numJugadores; i++) {
                addTropasJugador(i, 10);
            }
            JOptionPane.showMessageDialog(this, "Comienza el jugador 1", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            print("Comienza el jugador 1");
        }
        return true;
    }

    public void inicioTurno(int jugador){
        jugar.addTropasJugador(jugador, 3);
        agregarCarta(jugador);
        boolean[] regiones = jugar.comprobarRegiones(jugador);
        if(regiones[0]) jugar.addTropasJugador(jugador, 1);
        if(regiones[1]) jugar.addTropasJugador(jugador, 4);
        if(regiones[2]) jugar.addTropasJugador(jugador, 6);
        if(regiones[3]) jugar.addTropasJugador(jugador, 2);
        if(regiones[4]) jugar.addTropasJugador(jugador, 2);
        if(regiones[5]) jugar.addTropasJugador(jugador, 4);
        updateTropas(jugador);
    }

    public void siguienteJugadorInicio(){
        jugadorActual = jugar.siguienteJugador();
        juego.cambiarJugador(jugadorActual);
    }

    public void siguienteJugador(){
        jugadorActual = jugar.siguienteJugador();
        juego.cambiarJugador(jugadorActual);
        if(jugadoresRemovidos[jugadorActual]){
            siguienteJugador();
            return;
        }
        inicioTurno(jugadorActual);
        JOptionPane.showMessageDialog(this, "Turno del jugador " + (jugadorActual + 1),
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void comprobarAtaque(int atk, int target, int jugador)
            throws RiskException{
        jugar.comprobarAtaque(atk, target, jugador);
    }

    public void comprobarTransporte(int dptoA, int dptoB, int jugador) throws RiskException{
        jugar.comprobarTransporte(dptoA, dptoB, jugador);
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        jugar.atacar(atk, target, jugador, dado1, dado2);
    }

    public void print(String message){
        msg.print(message);
    }

    public int cambiarFase(){
        int fase = jugar.cambiarFase();
        juego.cambiarFase(fase);
        return fase;
    }

    public boolean comprobarVictoria(){
        int jugador = jugar.comprobarVictoria(jugadorActual);
        if(jugador > -1){
            JOptionPane.showMessageDialog(this, "El jugador " + jugador + " ha ganado la partida", "Victoria",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            return true;
        }
        return false;
    }

    public void comprobarJugadores(){
        for (int i = 0; i < numJugadores; i++) {
            if(jugar.comprobarJugador(i)) removeJugador(i);
        }
    }

    public void removeJugador(int jugador){
        if (!jugadoresRemovidos[jugador]) {
            JOptionPane.showMessageDialog(this, "El jugador " + (jugador + 1) + " ha perdido todos sus territorios",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            print("El jugador " + (jugador + 1) + " ha perdido todos sus territorios");
            jugadores.removerJugador(jugador);
            jugadoresRemovidos[jugador] = true;
        }
    }

    public boolean[] getJugadoresRemovidos(){
        return jugadoresRemovidos;
    }

    public void moverTropas(int idA, int idB, int cantidad){
        jugar.moverTropas(idA, idB, cantidad);
    }

    public boolean checkConquista(int target, int jugador){
        return jugar.checkConquista(target,jugador);
    }

    public boolean checkTerritorio(int dpto, int jugador){
        return jugar.checkTerritorio(dpto, jugador);
    }

    public int seleccionarCarta(int posCarta, int jugador){
        return jugar.seleccionarCarta(posCarta, jugador);
    }

    public void deseleccionarCarta(int posCarta, int jugador){
        jugar.deseleccionarCarta(posCarta, jugador);
    }

    public boolean checkContCartas(int jugador){
        return jugar.checkContCartas(jugador);
    }

    public void agregarCarta(int jugador){
        int carta = (int)(Math.random()*10 + 1);
        if (carta == 10) carta = 3;
        else carta %= 3;
        //int carta = (int)(Math.random()*4);
        jugadores.agregarCarta(jugador, carta);
        jugar.addCarta(jugador, carta);
    }

    public ArrayList<Integer> getCartasJugador(int jugador){
        return jugar.getCartasJugador(jugador);
    }

    public int getTropasJugador(int jugador){
        return jugar.getTropasJugador(jugador);
    }

    public int getJugadorDpto(int idDpto){
        return jugar.getDpto(idDpto)[0];
    }

    public int getTropasDpto(int idDpto){
        return jugar.getDpto(idDpto)[1];
    }

    public String getNombreDpto(int idDpto){
        return jugar.getNombreDpto(idDpto);
    }

    public String getRegionDpto(int idDpto){
        return jugar.getRegionDpto(idDpto);
    }

    public void addTropasDpto (int idDpto, int cantidad) throws RiskException{
        if(getTropasDpto(idDpto) + cantidad > 99) throw new RiskException("Un territorio no puede tener " +
                "más de 99 tropas");
        jugar.addTropasDpto(idDpto, cantidad);
        updateTropasDpto(idDpto);
    }

    public void reduceTropasDpto(int idDpto, int cantidad){
        jugar.reduceTropasDpto(idDpto,cantidad);
    }

    public void addTropasJugador (int jugador, int cantidad){
        jugar.addTropasJugador(jugador, cantidad);
        jugadores.updateTropas(jugador);
    }

    public boolean reduceTropasJugador (int jugador, int cantidad){
        if(jugar.getTropasJugador(jugador) == 0) return false;
        jugar.reduceTropasJugador(jugador, cantidad);
        jugadores.updateTropas(jugador);
        return true;
    }

    public void updateJugadorActual(){
        this.jugadorActual = jugar.getJugadorActual();
    }

    public void updateTropas(int jugador){
        jugadores.updateTropas(jugador);
    }

    public void updateTropasDpto(int dpto){
        juego.updateTropas(dpto);
    }

    public void update(){
        juego.update();
    }

    public void update(int dpto){
        juego.update(dpto);
    }

    public void update(int dptoA, int dptoB){
        juego.update(dptoA, dptoB);
    }

    public void update(int dptoA, int dptoB, boolean clickeado){
        juego.update(dptoA, dptoB, clickeado);
    }

    public void setMsgbox(Msgbox msg){
        this.msg = msg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(preinicio){
            if (jugadorActual == numJugadores-1){
                preinicio = false;
                jugadores.cambiarBoton("Siguiente Jugador");
                siguienteJugador();
                return;
            } else {
                siguienteJugadorInicio();
                return;
            }
        }
        int fase = cambiarFase();
        if(fase == 1){
            siguienteJugador();
            jugadores.cambiarBoton("Siguiente fase");
            JOptionPane.showMessageDialog(this, "Inicia fase de refuerzo", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else if (fase == 3){
            jugadores.cambiarBoton("Siguiente jugador");
            JOptionPane.showMessageDialog(this, "Inicia fase de transporte", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Inicia fase de ataque", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
