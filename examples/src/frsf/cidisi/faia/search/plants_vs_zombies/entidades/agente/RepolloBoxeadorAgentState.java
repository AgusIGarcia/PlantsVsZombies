package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Casillero;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.Girasol;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.PercepcionCasillero;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.RepolloBoxeadorPerception;

public class RepolloBoxeadorAgentState extends SearchBasedAgentState {
    private Casillero[][] jardin;
    private Posicion posicion;
    private Integer energia;
    private Integer zombiesEnElAmbiente;
    private InicioJuego parametrosInicio;

    public RepolloBoxeadorAgentState(InicioJuego parametrosInicio) {
        this.parametrosInicio = parametrosInicio;
        this.initState();
    }

    @Override
    public void initState() {
        this.inicializarJardin();
        this.posicion = this.parametrosInicio.posicionRepollo;
        this.energia = this.parametrosInicio.energiaRepollo;
        this.zombiesEnElAmbiente = 0;
    }

    private void inicializarJardin() {
        this.jardin = new Casillero[JardinEnvironmentState.FILAS_JARDIN][JardinEnvironmentState.COLUMNAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                this.jardin[fila][columna] = new Casillero();
            }
        }
    }

    public RepolloBoxeadorAgentState(RepolloBoxeadorAgentState estado) {
        this.copiarJardin(estado.getJardin());
        this.posicion = new Posicion(estado.getPosicion().fila, estado.getPosicion().columna);
        this.energia = estado.getEnergia();
        this.zombiesEnElAmbiente = estado.getZombiesPorMatar();
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

    @Override
    public void updateState(Perception p) {
        RepolloBoxeadorPerception percepcion = (RepolloBoxeadorPerception) p;
        this.consumirPercepcion(percepcion.getPercepcionArriba());
        this.consumirPercepcion(percepcion.getPercepcionAbajo());
        this.consumirPercepcion(percepcion.getPercepcionCentro());
        this.consumirPercepcion(percepcion.getPercepcionIzquierda());
        this.consumirPercepcion(percepcion.getPercepcionDerecha());
        this.energia = percepcion.getEnergiaAgente();
        this.zombiesEnElAmbiente = this.getCantidadZombiesPercibidos();
    }

    private void consumirPercepcion(PercepcionCasillero percepcion) {
        if (percepcion != null) {
            this.jardin[percepcion.posicion.fila][percepcion.posicion.columna] = percepcion.casillero;
        }
    }

    private Integer getCantidadZombiesPercibidos() {
        Integer cantidadZombies = 0;
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                if (this.jardin[fila][columna].zombie != null) {
                    cantidadZombies++;
                }
            }
        }
        return cantidadZombies;
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

    public void setPosicion(Posicion posicionDestino) {
        this.posicion = posicionDestino;
    }

    public Integer getEnergia() {
        return energia;
    }

    public void recolectarSoles() {
        Casillero casilleroActual = this.getCasilleroActual();
        this.energia += casilleroActual.cantidadDeSoles;
        casilleroActual.cantidadDeSoles = 0;
    }

    public Boolean sePuedePlantarGirasol() {
        return this.getCasilleroActual().girasol == null && this.getCasilleroActual().zombie != null
                && this.energia > 0;
    }

    public void plantarGirasol() {
        this.getCasilleroActual().girasol = new Girasol();
        this.energia--;
    }

    public Integer getZombiesPorMatar() {
        return this.zombiesEnElAmbiente;
    }

    public void perderEnergiaPorZombie() {
        Casillero casilleroActual = this.getCasilleroActual();
        if (casilleroActual.zombie != null) {
            this.energia -= casilleroActual.zombie.danioAlAgente();
        }
    }

    public Boolean puedoMatarZombie(Posicion posicionAAtacar) {
        Casillero casilleroAAtacar = this.jardin[posicionAAtacar.fila][posicionAAtacar.columna];
        return casilleroAAtacar.zombie != null && this.energia > (casilleroAAtacar.zombie.vida);
    }

    public void matarZombie(Posicion posicionAAtacar) {
        Casillero casilleroAAtacar = this.jardin[posicionAAtacar.fila][posicionAAtacar.columna];
        this.energia -= casilleroAAtacar.zombie.vida;
        casilleroAAtacar.zombie = null;
    }

    private Casillero getCasilleroActual() {
        return this.jardin[this.posicion.fila][this.posicion.columna];
    }

    public Boolean objetivoCumplido() {
        return this.zombiesEnElAmbiente == 0 && this.energia > 0;
    }
}
