/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *
 * @author Baxes
 */
public class Jugar{
    private ArrayList<ArrayList<Integer>> adyacencia;
    private ArrayList<Departamento> dptos;
    private ArrayList<Jugador> jugadores;
    private int numJugadores;
    private int jugadorActual;

    public Jugar(int numJugadores) throws RiskException, FileNotFoundException {
        //jugar = new Jugar();
        dptos = new ArrayList<Departamento>(32);
        for (int i = 0; i < 32; i++) {
            dptos.add(new Departamento(i));
        }
        adyacencia = new ArrayList<ArrayList<Integer>>(32);
        Scanner in = new Scanner(new File("res/dptos_adyacencia.txt"));
        String line;
        for (int i = 0; i < 32; i++) {
            line = in.nextLine();
            Scanner lineRead = new Scanner(line);
            int pos = lineRead.nextInt() - 1;
            if(pos != i){
                throw new RiskException("La lista de adyacencia txt no está"
                        + " correctamente definida");
            }
            ArrayList<Integer> dptoAdyacentes = new ArrayList<Integer>();
            while (lineRead.hasNext()) {
                dptoAdyacentes.add(lineRead.nextInt() - 1);
            }
            adyacencia.add(dptoAdyacentes);
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
        if(jugador == dptos.get(target).getIdJugador()){
            throw new RiskException("El territorio ya le pertenece al jugador");
        }
        if(jugador != dptos.get(atk).getIdJugador()){
            throw new RiskException("El territorio no pertenece al jugador");
        }
        if(dptos.get(atk).getNumTropas() <= 1){
            throw new RiskException("El territorio no tiene tropas suficientes"
                    + " para atacar");
        }
        if(dptos.get(target).getNumTropas() <= 0){
            throw new RiskException("Error, el territorio objetivo no tiene tropas");
        }
        if(!adyacencia.get(atk).contains(target)){
            throw new RiskException("El territorio a atacar no es adyacente al"
                    + " territorio del jugador");
        }
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        if(dado1 > dado2){
            dptos.get(target).reduceTropas();
        } else {
            dptos.get(atk).reduceTropas();
        }
    }

    public boolean checkConquista(int target, int jugador){
        if (dptos.get(target).getNumTropas() == 0){
            dptos.get(target).setIdJugador(jugador);
            return true;
        }
        return false;
    }

    public void setTipoCartaJugador(int id, int carta){
        jugadores.get(id).setTipoCarta(carta);
    }

    public int getTipoCartaJugador(int id){
        return jugadores.get(id).getTipoCarta();
    }
    
    public void addTropas(int dpto){
        dptos.get(dpto).addTropas();
    }
    
    public void reduceTropas(int dpto){
        dptos.get(dpto).reduceTropas();
    }
    
    public void setJugador(int dpto, int jugador){
        dptos.get(dpto).setIdJugador(jugador);
    }
    
    public int[] getDpto(int idDpto){
        int[] datos = new int[2];
        datos[0] = dptos.get(idDpto).getIdJugador();
        datos[1] = dptos.get(idDpto).getNumTropas();
        return datos;
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
