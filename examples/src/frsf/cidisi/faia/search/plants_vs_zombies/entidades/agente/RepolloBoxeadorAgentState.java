package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Casillero;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.PercepcionCasillero;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.RepolloBoxeadorPerception;

public class RepolloBoxeadorAgentState extends SearchBasedAgentState {
    private Casillero[][] jardin;
    private Posicion posicion;
    private Integer energia;
    private Integer zombiesPorMatar;
    private InicioJuego parametrosInicio;

    public RepolloBoxeadorAgentState(InicioJuego inicio) {
        this.parametrosInicio = inicio;
        this.initState();
    }

    public RepolloBoxeadorAgentState(RepolloBoxeadorAgentState estado) {
        this.copiarJardin(estado.getJardin());
        this.posicion = new Posicion(estado.getPosicion().fila, estado.getPosicion().columna);
        this.energia = estado.getEnergia();
        this.zombiesPorMatar = estado.getZombiesPorMatar();
    }

    @Override
    public void initState() {
        this.jardin = new Casillero[JardinEnvironmentState.FILAS_JARDIN][JardinEnvironmentState.COLUMNAS_JARDIN];
        this.posicion = this.parametrosInicio.posicionRepollo;
        this.energia = this.parametrosInicio.energiaRepollo;
        this.zombiesPorMatar = this.parametrosInicio.cantidadZombiesAGenerar
                + this.parametrosInicio.cantidadZombiesInicial;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RepolloBoxeadorAgentState) ? this.equals(((RepolloBoxeadorAgentState) obj)) : false;
    }

    public boolean equals(RepolloBoxeadorAgentState otroEstado) {
        return this.posicionesIguales(otroEstado.getPosicion()) && this.energiaIgual(otroEstado.getEnergia())
                && this.jardinesIguales(otroEstado.getJardin());
    }

    private boolean posicionesIguales(Posicion otraPosicion) {
        return this.posicion.equals(otraPosicion);
    }

    private boolean energiaIgual(Integer otraEnergia) {
        return this.energia == otraEnergia;
    }

    private boolean jardinesIguales(Casillero[][] otroJardin) {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_FILA; columna++) {
                if (!this.jardin[fila][columna].equals(otroJardin[fila][columna])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public SearchBasedAgentState clone() {
        return new RepolloBoxeadorAgentState(this);
    }

    private void copiarJardin(Casillero[][] jardinACopiar) {
        this.jardin = new Casillero[JardinEnvironmentState.FILAS_JARDIN][JardinEnvironmentState.COLUMNAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_FILA; columna++) {
                this.jardin[fila][columna] = new Casillero(jardinACopiar[fila][columna]);
            }
        }
    }

    @Override
    public void updateState(Perception p) {
        RepolloBoxeadorPerception percepcion = (RepolloBoxeadorPerception) p;
        this.consumirPercepcion(percepcion.getPercepcionArriba());
        this.consumirPercepcion(percepcion.getPercepcionAbajo());
        this.consumirPercepcion(percepcion.getPercepcionCentro());
        this.consumirPercepcion(percepcion.getPercepcionIzquierda());
        this.consumirPercepcion(percepcion.getPercepcionDerecha());
        this.energia = percepcion.getEnergiaAgente();
        this.zombiesPorMatar = percepcion.getCantidadZombiesRestantes();
    }

    private void consumirPercepcion(PercepcionCasillero percepcion) {
        if (percepcion != null) {
            this.jardin[percepcion.posicion.fila][percepcion.posicion.columna] = percepcion.casillero;
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    public Casillero[][] getJardin() {
        return jardin;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Integer getEnergia() {
        return energia;
    }

    public Integer getZombiesPorMatar() {
        return this.zombiesPorMatar;
    }

}
