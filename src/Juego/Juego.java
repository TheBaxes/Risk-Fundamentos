/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
/**
 *
 * @author Baxes
 */
public class Juego {
    private ArrayList<ArrayList<Integer>> adyacencia;
    private ArrayList<Departamento> dptos;
    private ArrayList<Jugador> jugadores;
    private int numJugadores;
    private int jugadorActual;
    private int fase;

    public Juego(int numJugadores) throws RiskException, IOException {
        dptos = new ArrayList<>(32);
        adyacencia = new ArrayList<>(32);
        Scanner in = new Scanner(new File("res/dptos_adyacencia.txt"));
        Scanner nombre = new Scanner(Paths.get("res/dptos_nombres.txt"), StandardCharsets.ISO_8859_1.name());
        Scanner region = new Scanner(Paths.get("res/dptos_regiones.txt"), StandardCharsets.ISO_8859_1.name());
        String line;
        for (int i = 0; i < 32; i++) {
            line = in.nextLine();
            Scanner lineRead = new Scanner(line);
            int pos = lineRead.nextInt() - 1;
            int pos2 = nombre.nextInt() - 1;
            int pos3 = region.nextInt() - 1;
            if(pos != i && pos2 != i && pos3 != i){
                throw new RiskException("La lista de adyacencia o de nombres no está"
                        + " correctamente definida");
            }
            ArrayList<Integer> dptoAdyacentes = new ArrayList<>();
            while (lineRead.hasNext()) {
                dptoAdyacentes.add(lineRead.nextInt() - 1);
            }
            adyacencia.add(dptoAdyacentes);

            dptos.add(new Departamento(i, nombre.nextLine().substring(1), region.nextLine().substring(1)));
        }

        jugadores = new ArrayList<>(numJugadores);
        for (int i = 0; i < numJugadores; i++) {
            jugadores.add(new Jugador(i));
        }
        
        this.numJugadores = numJugadores;
        jugadorActual = 0;
    }
    
    public void comprobarAtaque(int atk, int target, int jugador)
            throws RiskException {
        if(checkTerritorio(target, jugador)){
            throw new RiskException(target + " ya le pertenece al jugador");
        }
        if(!checkTerritorio(atk, jugador)){
            throw new RiskException(atk + " no pertenece al jugador");
        }
        if(dptos.get(atk).getNumTropas() <= 1){
            throw new RiskException(atk + " no tiene tropas suficientes para continuar");
        }
        if(dptos.get(target).getNumTropas() <= 0){
            throw new RiskException(target + " no tiene tropas para ser atacado");
        }
        if(!adyacencia.get(atk).contains(target)){
            throw new RiskException(target + " no es adyacente a" + atk);
        }
    }

    public boolean checkTerritorio(int dpto, int jugador){
        return jugador == dptos.get(dpto).getIdJugador();
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        if(dado1 > dado2){
            reduceTropas(target, 1);
        } else {
            reduceTropas(atk, 1);
        }
    }

    public boolean checkConquista(int target, int jugador){
        if (dptos.get(target).getNumTropas() == 0){
            dptos.get(target).setIdJugador(jugador);
            return true;
        }
        return false;
    }

    public void moverTropas(int idA, int idB, int cantidad){
        addTropas(idB, cantidad);
        reduceTropas(idA, cantidad);
    }

    public int cambiarFase(){
        fase++;
        if(fase == 4) fase = 1;
        return fase;
    }

    public void checkInicio(){

    }

    public void setTipoCartaJugador(int id, int carta){
        jugadores.get(id).setTipoCarta(carta);
    }

    public int getTipoCartaJugador(int id){
        return jugadores.get(id).getTipoCarta();
    }
    
    public void addTropas(int dpto, int cantidad){
        dptos.get(dpto).addTropas(cantidad);
    }
    
    public void reduceTropas(int dpto, int cantidad){
        dptos.get(dpto).reduceTropas(cantidad);
    }
    
    public void setJugador(int dpto, int jugador){
        dptos.get(dpto).setIdJugador(jugador);
    }

    /**
     * Obtener datos del departamento
     * @param idDpto Id del departamento
     * @return Array en el cual la posición 0 es la id del jugador y la posición 1 es el número de tropas
     */
    public int[] getDpto(int idDpto){
        int[] datos = new int[2];
        datos[0] = dptos.get(idDpto).getIdJugador();
        datos[1] = dptos.get(idDpto).getNumTropas();
        return datos;
    }
    
    public String getNombreDpto(int idDpto){
        return dptos.get(idDpto).getNombre();
    }

    public String getRegionDpto(int idDpto){
        return dptos.get(idDpto).getRegion();
    }

    @Override
    public String toString() {
        return "Dptos{" + "dptos=" + dptos + '}';
    }

    public int getNumJugadores(){
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores){
        this.numJugadores = numJugadores;
    }

    public int getJugadorActual(){
        return jugadorActual;
    }

    public void setJugadorActual(int jugadorActual){
        this.jugadorActual = jugadorActual;
    }
    
    
}
