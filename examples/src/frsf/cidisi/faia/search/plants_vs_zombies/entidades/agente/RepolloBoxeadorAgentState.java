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
    private Boolean[] filasVisitadas;
    // private Integer girasolesPlantados;

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
        // this.girasolesPlantados = 0;
        this.inicializarFilasVisitadas();
    }

    private void inicializarJardin() {
        this.jardin = new Casillero[JardinEnvironmentState.FILAS_JARDIN][JardinEnvironmentState.COLUMNAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                this.jardin[fila][columna] = new Casillero();
            }
        }
    }

    private void inicializarFilasVisitadas() {
        this.filasVisitadas = new Boolean[JardinEnvironmentState.FILAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            this.filasVisitadas[fila] = false;
        }
        this.actualizarFilasVisitadas();
    }

    public void actualizarFilasVisitadas() {
        this.filasVisitadas[this.posicion.fila] = true;
    }

    public RepolloBoxeadorAgentState(RepolloBoxeadorAgentState estado) {
        this.copiarJardin(estado.getJardin());
        this.posicion = new Posicion(estado.getPosicion().fila, estado.getPosicion().columna);
        this.energia = estado.getEnergia();
        this.zombiesEnElAmbiente = estado.getZombiesPorMatar();
        this.copiarFilasVisitadas(estado.getFilasVisitadas());
        // this.girasolesPlantados = estado.girasolesPlantados;
    }

    private void copiarJardin(Casillero[][] jardinACopiar) {
        this.jardin = new Casillero[JardinEnvironmentState.FILAS_JARDIN][JardinEnvironmentState.COLUMNAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                this.jardin[fila][columna] = new Casillero(jardinACopiar[fila][columna]);
            }
        }
    }

    private void copiarFilasVisitadas(Boolean[] filasVisitadasACopiar) {
        this.filasVisitadas = new Boolean[JardinEnvironmentState.FILAS_JARDIN];
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (filasVisitadasACopiar[fila]) {
                this.filasVisitadas[fila] = true;
            } else {
                this.filasVisitadas[fila] = false;
            }

        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RepolloBoxeadorAgentState) ? this.equals(((RepolloBoxeadorAgentState) obj)) : false;
    }

    public boolean equals(RepolloBoxeadorAgentState otroEstado) {
        return this.posicionesIguales(otroEstado.getPosicion()) && this.energiaIgual(otroEstado.getEnergia()) &&
                this.filasVisitadasIguales(otroEstado.getFilasVisitadas()) // &&
                                                                           // this.girasolesPlantadosIguales(otroEstado.getGirasolesPlantados())
                && this.jardinesIguales(otroEstado.getJardin());
    }

    private boolean posicionesIguales(Posicion otraPosicion) {
        return this.posicion.equals(otraPosicion);
    }

    private boolean energiaIgual(Integer otraEnergia) {
        return this.energia == otraEnergia;
    }

    private boolean filasVisitadasIguales(Boolean[] otroFilasVisitadas) {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.filasVisitadas[fila] != otroFilasVisitadas[fila]) {
                return false;
            }
        }
        return true;
    }

    // private boolean girasolesPlantadosIguales(Integer otrosGirasolesPlantados) {
    // return this.girasolesPlantados == otrosGirasolesPlantados;
    // }

    private boolean jardinesIguales(Casillero[][] otroJardin) {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
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
        // this.girasolesPlantados = this.getCantidadGirasolesPercibidos();
        reiniciarFilasVisitadas();
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

    private Integer getCantidadGirasolesPercibidos() {
        Integer cantidadGirasoles = 0;
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                if (this.jardin[fila][columna].girasol != null) {
                    cantidadGirasoles++;
                }
            }
        }
        return cantidadGirasoles;
    }

    private void reiniciarFilasVisitadas() {
        if (this.todasLasFilasVisitadas()) {
            // if (this.primerYUltimaFilaVisitada()) {
            this.inicializarFilasVisitadas();
        }
    }

    @Override
    public String toString() {
        String result = "Vista jardin Agente: \n";
        result += "------------------------------------------------------------------------------------------------------------------";
        result += "\n";
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                if (this.posicion.fila == fila && this.posicion.columna == columna) {
                    result += "(P," + this.jardin[fila][columna].toString() + ")";
                } else {
                    result += "(_," + this.jardin[fila][columna].toString() + ")";
                }
            }
            result += "\n";
        }
        result += "------------------------------------------------------------------------------------------------------------------";
        result += "\n";
        result += "Energía: " + this.energia + "\n";
        result += "Zombies detectados: " + this.zombiesEnElAmbiente + "\n";
        result += "Filas visitadas: " + "\n";
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.filasVisitadas[fila]) {
                result += "T ";
            } else {
                result += "F ";
            }
        }
        result += "\n";
        // result += "Girasoles plantados: " + this.girasolesPlantados + "\n";
        return result;
    }

    public Casillero[][] getJardin() {
        return jardin;
    }

    public Boolean[] getFilasVisitadas() {
        return this.filasVisitadas;
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
        return this.getCasilleroActual().girasol == null && this.getCasilleroActual().zombie == null
                && this.energia > 0;
    }

    public void plantarGirasol() {
        this.getCasilleroActual().girasol = new Girasol();
        this.energia--;
    }

    // private Integer getGirasolesPlantados() {
    // return this.girasolesPlantados;
    // }

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
        this.zombiesEnElAmbiente--;
    }

    private Casillero getCasilleroActual() {
        return this.jardin[this.posicion.fila][this.posicion.columna];
    }

    public Boolean objetivoCumplido() {
        return this.todasLasFilasVisitadas() && this.zombiesEnElAmbiente == 0 && this.energia > 0; // &&
                                                                                                   // this.girasolesPlantados
                                                                                                   // >= 5;
    }

    private Boolean todasLasFilasVisitadas() {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (!this.filasVisitadas[fila])
                return false;
        }
        return true;
    }

    // private Boolean primerYUltimaFilaVisitada() {
    // return this.filasVisitadas[JardinEnvironmentState.PRIMERA_FILA]
    // && this.filasVisitadas[JardinEnvironmentState.ULTIMA_FILA];
    // }

}
