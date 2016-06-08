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
 * Clase Risk
 * @author Sebastián Patiño Barrientos
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

    /**
     * Crea una instancia de la clase Risk, la cual es la base de la interfaz de juego
     * @param numJugadores Número de jugadores
     */
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

    /**
     * Inicia la fase de selección
     */
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

    /**
     * Ejecuta la selección de territorio de la primera fase. En caso de que todos los territorios estén ocupados,
     * cambia a la siguiente fase
     * @param dpto Departamento a seleccionar por el jugador actual
     * @return booleano que retorna falso si no se pudo seleccionar el territorio
     */
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
                    "usar cuando quieras. (El escudo de Colombia se puede usar como comodín) Además, las tropas",
                    "que no uses en un turno se guardan para que las uses después. Usa click izquierdo para",
                    "adicionar una tropa a un territorio y click derecho para retirar una tropa.",
                    "Para acabar esta fase presiona el botón que dice Siguiente fase."
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

    /**
     * Método que ejecuta los procesos que deben hacerse al comienzo de un turno
     * @param jugador Jugador que comienza el turno
     */
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

    /**
     * Método que cambia al siguiente jugador en la primera fase del juego
     */
    public void siguienteJugadorInicio(){
        jugadorActual = jugar.siguienteJugador();
        juego.cambiarJugador(jugadorActual);
    }

    /**
     * Método que cambia al siguiente jugador en el resto de fases del juego
     */
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

    /**
     * Método que comprueba un ataque
     * @param atk Departamento atacante
     * @param target Departamento objetivo
     * @param jugador Jugador atacante
     * @throws RiskException Aviso en caso de que no se pueda ejecutar el ataque
     */
    public void comprobarAtaque(int atk, int target, int jugador) throws RiskException{
        jugar.comprobarAtaque(atk, target, jugador);
    }

    /**
     * Método que comprueba un transporte de tropas entre dos territorios
     * @param dptoA Departamento que envía tropas
     * @param dptoB Departamento receptor
     * @param jugador Jugador que debe ser dueño de ambos departamentos
     * @throws RiskException Aviso en caso de que no se pueda ejecutar el movimiento
     */
    public void comprobarTransporte(int dptoA, int dptoB, int jugador) throws RiskException{
        jugar.comprobarTransporte(dptoA, dptoB, jugador);
    }

    /**
     * Método que ejecuta un ataque entre dos territorios
     * @param atk Departamento atacante
     * @param target Departamento objetivo
     * @param jugador Jugador atacante
     * @param dado1 Valor de dado atacante
     * @param dado2 Valor de dado objetivo
     */
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        jugar.atacar(atk, target, jugador, dado1, dado2);
    }

    /**
     * Método que muestra un mensaje en la caja de registros
     * @param message Mensaje a mostrar
     */
    public void print(String message){
        msg.print(message);
    }

    /**
     * Método que cambia la fase actual
     * @return int con la fase actual
     */
    public int cambiarFase(){
        int fase = jugar.cambiarFase();
        juego.cambiarFase(fase);
        return fase;
    }

    /**
     * Método que comprueba si el jugador actual ha ganado la partida. En caso de ser cierto, se cierra la ventana
     * de juego y se abre una nueva instancia de MenuPrincipal
     * @return booleano que si es verdadero entonces algún jugador ganó la partida
     */
    public boolean comprobarVictoria(){
        int jugador = jugar.comprobarVictoria(jugadorActual);
        if(jugador > -1){
            JOptionPane.showMessageDialog(this, "El jugador " + jugador + " ha ganado la partida", "Victoria",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new MenuPrincipal();
            return true;
        }
        return false;
    }

    /**
     * Método que comprueba si algún jugador no tiene más territorios y lo remueve en caso de ser cierto
     */
    public void comprobarJugadores(){
        for (int i = 0; i < numJugadores; i++) {
            if(jugar.comprobarJugador(i)) removeJugador(i);
        }
    }

    /**
     * Método que remueve un jugador de la partida
     * @param jugador Jugador a remover
     */
    public void removeJugador(int jugador){
        if (!jugadoresRemovidos[jugador]) {
            JOptionPane.showMessageDialog(this, "El jugador " + (jugador + 1) + " ha perdido todos sus territorios",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            print("El jugador " + (jugador + 1) + " ha perdido todos sus territorios");
            jugadores.removerJugador(jugador);
            jugadoresRemovidos[jugador] = true;
        }
    }

    /**
     * Método que retorna que jugadores han sido removidos de la partida
     * @return arreglo de booleanos en el cual si una posición es verdadera el jugador respectivo ha sido eliminado
     */
    public boolean[] getJugadoresRemovidos(){
        return jugadoresRemovidos;
    }

    /**
     * Método que mueve tropas entre dos territorios
     * @param idA Departamento que enviará tropas
     * @param idB Departamento receptor
     * @param cantidad Cantidad de tropas
     */
    public void moverTropas(int idA, int idB, int cantidad){
        jugar.moverTropas(idA, idB, cantidad);
    }

    /**
     * Método que comprueba y ejecuta la conquista de un territorio
     * @param target Departamento a conquistar
     * @param jugador Jugador atacante
     * @return Retorna verdadero si se ejecuta la conquista
     */
    public boolean checkConquista(int target, int jugador){
        return jugar.checkConquista(target,jugador);
    }

    /**
     * Método que comprueba si un territorio pertenece a un jugador
     * @param dpto Departamento a comprobar
     * @param jugador Jugador a comprobar
     * @return booleano que es verdadero si el territorio pertenece
     */
    public boolean checkTerritorio(int dpto, int jugador){
        return jugar.checkTerritorio(dpto, jugador);
    }

    /**
     * Método que selecciona una carta de un jugador
     * @param posCarta Posición de la carta a seleccionar
     * @param jugador Jugador que posee la carta
     * @return Valor de la carta en la posición seleccionada
     */
    public int seleccionarCarta(int posCarta, int jugador){
        return jugar.seleccionarCarta(posCarta, jugador);
    }

    /**
     * Método que deselecciona una carta de un jugador
     * @param posCarta Posición de la carta a deseleccionar
     * @param jugador Jugador que posee la carta
     */
    public void deseleccionarCarta(int posCarta, int jugador){
        jugar.deseleccionarCarta(posCarta, jugador);
    }

    /**
     * Método que comprueba si las cartas seleccionadas de un jugador pueden gastarse y si es verdadero las usa
     * @param jugador Jugador
     * @return boolean que es verdadero si se usaron las cartas seleccionadas
     */
    public boolean checkContCartas(int jugador){
        return jugar.checkContCartas(jugador);
    }

    /**
     * Método que agrega una carta aleatoria a un jugador, excepto si este posee 5
     * @param jugador Jugador a agregar carta
     */
    public void agregarCarta(int jugador){
        int carta = (int)(Math.random()*10 + 1);
        if (carta == 10) carta = 3;
        else carta %= 3;
        //int carta = (int)(Math.random()*4);
        if(jugar.getCartasJugador(jugador).size() < 5) {
            jugadores.agregarCarta(jugador, carta);
            jugar.addCarta(jugador, carta);
        }
    }

    /**
     * Método que retorna las cartas que posee un jugador
     * @param jugador Jugador
     * @return ArrayList con las cartas del jugador
     */
    public ArrayList<Integer> getCartasJugador(int jugador){
        return jugar.getCartasJugador(jugador);
    }

    /**
     * Método que retorna las tropas que contine un jugador
     * @param jugador Jugador
     * @return int con el valor de las tropas
     */
    public int getTropasJugador(int jugador){
        return jugar.getTropasJugador(jugador);
    }

    /**
     * Método que retorna el jugador asignado a un territorio
     * @param idDpto Departamento a revisar
     * @return int con el id del jugador
     */
    public int getJugadorDpto(int idDpto){
        return jugar.getDpto(idDpto)[0];
    }

    /**
     * Método que retorna la cantidad de tropas de un territorio
     * @param idDpto Departamento a revisar
     * @return int con el valor de las tropas
     */
    public int getTropasDpto(int idDpto){
        return jugar.getDpto(idDpto)[1];
    }

    /**
     * Método que retorna el nombre de un departamento
     * @param idDpto Departamento
     * @return String con el nombre
     */
    public String getNombreDpto(int idDpto){
        return jugar.getNombreDpto(idDpto);
    }

    /**
     * Método que retorna la región de un departamento
     * @param idDpto Departamento
     * @return String con la region
     */
    public String getRegionDpto(int idDpto){
        return jugar.getRegionDpto(idDpto);
    }

    /**
     * Método que agrega tropas a un territorio
     * @param idDpto Departamento
     * @param cantidad Cantidad de tropas
     * @throws RiskException Error al agregar tropas
     */
    public void addTropasDpto (int idDpto, int cantidad) throws RiskException{
        if(getTropasDpto(idDpto) + cantidad > 99) throw new RiskException("Un territorio no puede tener " +
                "más de 99 tropas");
        jugar.addTropasDpto(idDpto, cantidad);
        updateTropasDpto(idDpto);
    }

    /**
     * Método que quita tropas a un territorio
     * @param idDpto Departamento a remover tropas
     * @param cantidad Cantidad
     */
    public void reduceTropasDpto(int idDpto, int cantidad){
        jugar.reduceTropasDpto(idDpto,cantidad);
    }

    /**
     * Método que adiciona tropas a un jugador
     * @param jugador Jugador
     * @param cantidad Cantidad de tropas
     */
    public void addTropasJugador (int jugador, int cantidad){
        jugar.addTropasJugador(jugador, cantidad);
        jugadores.updateTropas(jugador);
    }

    /**
     * Método que quita tropas a un jugador
     * @param jugador Jugador
     * @param cantidad Cantidad de tropas
     * @return retorna falso si el jugador no posee tropas para remover
     */
    public boolean reduceTropasJugador (int jugador, int cantidad){
        if(jugar.getTropasJugador(jugador) == 0) return false;
        jugar.reduceTropasJugador(jugador, cantidad);
        jugadores.updateTropas(jugador);
        return true;
    }

    /**
     * Método que actualiza la cantidad de tropas de un jugador
     * @param jugador Jugador
     */
    public void updateTropas(int jugador){
        jugadores.updateTropas(jugador);
    }

    /**
     * Método que actualiza la cantidad de tropas de un territorio
     * @param dpto Departamento
     */
    public void updateTropasDpto(int dpto){
        juego.updateTropas(dpto);
    }

    /**
     * Método que actualiza la interfaz (Causa mucho lag)
     */
    public void update(){
        juego.update();
    }

    /**
     * Método que actualiza un departamento
     * @param dpto Departamento
     */
    public void update(int dpto){
        juego.update(dpto);
    }

    /**
     * Método que actualiza las tropas de dos departamentos
     * @param dptoA Departamento 1
     * @param dptoB Departamento 2
     */
    public void update(int dptoA, int dptoB){
        juego.update(dptoA, dptoB);
    }

    /**
     * Método que actualiza las tropas y el estado de dos departamentos
     * @param dptoA Departamento 1
     * @param dptoB Departamento 2
     * @param clickeado Estado de los departamentos
     */
    public void update(int dptoA, int dptoB, boolean clickeado){
        juego.update(dptoA, dptoB, clickeado);
    }

    /**
     * Método que asigna una caja de registros a la clase
     * @param msg Caja de registro
     */
    public void setMsgbox(Msgbox msg){
        this.msg = msg;
    }

    /**
     * Método que recibe y ejecuta un evento
     * @param e Evento
     */
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
                JOptionPane.showMessageDialog(this, "Turno del jugador " + (jugadorActual + 1),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
