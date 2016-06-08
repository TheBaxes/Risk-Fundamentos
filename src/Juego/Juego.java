/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import Interfaz.Risk;

/**
 *
 * @author Baxes
 */
public class Juego {
    private ArrayList<ArrayList<Integer>> adyacencia;
    private ArrayList<Departamento> dptos;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Departamento> dptosNeutros;

    private int insular;
    private ArrayList<Integer> caribe;
    private ArrayList<Integer> andina;
    private ArrayList<Integer> pacifica;
    private ArrayList<Integer> orinoquia;
    private ArrayList<Integer> amazonica;
    private int selectedDptos;
    private int numJugadores;
    private int jugadorActual;
    private int fase;
    private Risk risk;

    public Juego(int numJugadores, Risk risk) throws RiskException, IOException {
        dptos = new ArrayList<>(32);
        adyacencia = new ArrayList<>(32);
        Scanner in = new Scanner(new File("res/dptos_adyacencia.txt"));
        Scanner nombre = new Scanner(Paths.get("res/dptos_nombres.txt"), StandardCharsets.ISO_8859_1.name());
        Scanner region = new Scanner(Paths.get("res/dptos_regiones.txt"), StandardCharsets.ISO_8859_1.name());
        String line;

        insular = 0;
        caribe = new ArrayList<>();
        andina = new ArrayList<>();
        pacifica = new ArrayList<>();
        orinoquia = new ArrayList<>();
        amazonica = new ArrayList<>();
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

            String regionNombre = region.nextLine().substring(1);
            dptos.add(new Departamento(i, nombre.nextLine().substring(1), regionNombre));
            switch (regionNombre){
                case "Insular":
                    insular = i;
                    break;
                case "Caribe":
                    caribe.add(i);
                    break;
                case "Andina":
                    andina.add(i);
                    break;
                case "Pacífica":
                    pacifica.add(i);
                    break;
                case "Orinoquía":
                    orinoquia.add(i);
                    break;
                case "Amazónica":
                    amazonica.add(i);
                    break;
            }
        }

        dptosNeutros = new ArrayList<>(dptos);

        jugadores = new ArrayList<>(numJugadores);
        for (int i = 0; i < numJugadores; i++) {
            jugadores.add(new Jugador(i));
        }
        
        this.numJugadores = numJugadores - 1;
        jugadorActual = 0;

        this.risk = risk;
    }

    public boolean seleccionTerritorio(int dpto){
        if(getDpto(dpto)[0] != -1) return false;
        setJugador(dpto, jugadorActual);
        addTropasDpto(dpto, 1);
        jugadores.get(jugadorActual).addDptos();
        risk.update(dpto);
        selectedDptos++;
        risk.print("El jugador " + (jugadorActual + 1) + " ha seleccionado " + risk.getNombreDpto(dpto));
        if(numJugadores == 1) {
            for (int i = 0; i < dptosNeutros.size(); i++) {
                if(dptosNeutros.get(i).getId() == dpto) dptosNeutros.remove(i);
            }
            if(jugadorActual == 1 && selectedDptos < 32){
                randomEmptyDpto();
            }
        }
        risk.siguienteJugadorInicio();
        return true;
    }

    public void randomEmptyDpto(){
        int selection = (int)(Math.random()*dptosNeutros.size());
        int dptoRandom = dptosNeutros.get(selection).getId();
        setJugador(dptoRandom, 4);
        addTropasDpto(dptoRandom, 1);
        dptosNeutros.remove(selection);
        risk.update(dptoRandom);
        selectedDptos++;
    }

    public int siguienteJugador(){
        if(jugadorActual >= numJugadores) jugadorActual = 0;
        else jugadorActual++;
        return jugadorActual;
    }

    public int cambiarFase(){
        if (fase == 0) {
            if (selectedDptos == 32) fase = 1;
        } else fase++;
        if(fase == 4) fase = 1;
        return fase;
    }

    public void comprobarAtaque(int atk, int target, int jugador) throws RiskException {
        if(checkTerritorio(target, jugador)){
            throw new RiskException(risk.getNombreDpto(target) + " ya le pertenece al jugador");
        }
        if(!checkTerritorio(atk, jugador)){
            throw new RiskException(risk.getNombreDpto(atk) + " no pertenece al jugador");
        }
        if(dptos.get(atk).getNumTropas() <= 1){
            throw new RiskException(risk.getNombreDpto(atk) + " no tiene tropas suficientes para continuar");
        }
        if(dptos.get(target).getNumTropas() <= 0){
            throw new RiskException(risk.getNombreDpto(target) + " no tiene tropas para ser atacado");
        }
        if(!adyacencia.get(atk).contains(target)){
            throw new RiskException(risk.getNombreDpto(target) + " no es adyacente a " + risk.getNombreDpto(atk));
        }
    }

    public void comprobarTransporte(int dptoA, int dptoB, int jugador) throws RiskException{
        if (getDpto(dptoA)[0] != jugador){
            throw new RiskException(risk.getNombreDpto(dptoA) + " no le pertenece al jugador");
        }
        if (getDpto(dptoB)[0] != jugador){
            throw new RiskException(risk.getNombreDpto(dptoB) + " no le pertenece al jugador");
        }
    }

    public boolean checkTerritorio(int dpto, int jugador){
        return jugador == dptos.get(dpto).getIdJugador();
    }
    
    public void atacar(int atk, int target, int jugador, int dado1, int dado2){
        if(dado1 > dado2){
            reduceTropasDpto(target, 1);
        } else {
            reduceTropasDpto(atk, 1);
        }
    }

    public boolean checkConquista(int target, int jugador){
        if (dptos.get(target).getNumTropas() == 0){
            jugadores.get(dptos.get(target).getIdJugador()).removeDptos();
            dptos.get(target).setIdJugador(jugador);
            jugadores.get(jugador).addDptos();
            return true;
        }
        return false;
    }

    public void moverTropas(int idA, int idB, int cantidad){
        addTropasDpto(idB, cantidad);
        reduceTropasDpto(idA, cantidad);
    }

    public boolean[] comprobarRegiones(int jugador){
        boolean[] territorios = new boolean[6];
        if(getDpto(insular)[0] != jugador) territorios[0] = true;
        territorios[0] = !territorios[0];
        for (Integer dpto: caribe){
            if(getDpto(dpto)[0] != jugador) territorios[1] = true;
        }
        territorios[1] = !territorios[1];
        for (Integer dpto: andina){
            if(getDpto(dpto)[0] != jugador) territorios[2] = true;
        }
        territorios[2] = !territorios[2];
        for (Integer dpto: pacifica){
            if(getDpto(dpto)[0] != jugador) territorios[3] = true;
        }
        territorios[3] = !territorios[3];
        for (Integer dpto: orinoquia){
            if(getDpto(dpto)[0] != jugador) territorios[4] = true;
        }
        territorios[4] = !territorios[4];
        for (Integer dpto: amazonica){
            if(getDpto(dpto)[0] != jugador) territorios[5] = true;
        }
        territorios[5] = !territorios[5];
        return territorios;
    }

    public int comprobarVictoria(int jugador){
        risk.comprobarJugadores();
        boolean[] jugadoresR = risk.getJugadoresRemovidos();
        int cont = 0;
        for (int i = 0; i <= numJugadores; i++) {
            if(jugadoresR[i]) cont++;
        }
        if(cont == numJugadores) return jugador + 1;
        for (int i = 0; i < 32; i++) {
            if(getDpto(i)[0] != jugador) return -1;
        }
        return jugador + 1;
    }

    public boolean comprobarJugador(int jugador){
        return jugadores.get(jugador).getDptos() == 0;
    }

    public int seleccionarCarta(int posCarta, int jugador){
        return jugadores.get(jugador).seleccionarCarta(posCarta);
    }

    public void deseleccionarCarta(int posCarta, int jugador){
        jugadores.get(jugador).deseleccionarCarta(posCarta);
    }

    public boolean checkContCartas(int jugador){
        return jugadores.get(jugador).checkContCartas();
    }

    public void addCarta(int jugador, int carta){
        jugadores.get(jugador).addCarta(carta);
    }

    public ArrayList<Integer> getCartasJugador(int jugador){
        return jugadores.get(jugador).getCartas();
    }

    public int getTropasJugador(int jugador){
        return jugadores.get(jugador).getTropas();
    }

    public void addTropasJugador(int jugador, int cantidad){
        jugadores.get(jugador).addTropas(cantidad);
    }

    public void reduceTropasJugador(int jugador, int cantidad){
        jugadores.get(jugador).removeTropas(cantidad);
    }

    public void addTropasDpto(int dpto, int cantidad){
        dptos.get(dpto).addTropas(cantidad);
    }
    
    public void reduceTropasDpto(int dpto, int cantidad){
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

    public int getJugadorActual(){
        return jugadorActual;
    }
    
    
}
