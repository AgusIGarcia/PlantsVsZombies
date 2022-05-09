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
    private Integer vidaZombiesPercibidos;
    private InicioJuego parametrosInicio;
    private Boolean[] filasVisitadas;
    private Boolean planteGirasol;
    private Boolean deboMatarZombie;
    private Boolean meMovi;
    private Integer turno;
    private Integer costo;
    private Integer energiaInicial;
    private Integer girasolesPrimeraColumnaIniciales;
    private boolean mateZombie;

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
        this.vidaZombiesPercibidos = 0;
        this.planteGirasol = false;
        this.turno = 0;
        this.costo = 0;
        this.deboMatarZombie = false;
        this.meMovi = false;
        this.setMateZombie(false);
        this.setGirasolesPrimeraColumnaIniciales(100);
        this.setEnergiaInicial(this.parametrosInicio.energiaRepollo);
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
        this.vidaZombiesPercibidos = estado.getVidaZombiesPercibidos();
        this.planteGirasol = estado.getPlanteGirasol();
        this.turno = estado.getTurno();
        this.deboMatarZombie = estado.getDeboMatarZombie();
        this.meMovi = estado.getMeMovi();
        this.costo = estado.getCosto();
        this.mateZombie = estado.getMateZombie();
        this.energiaInicial = estado.getEnergiaInicial();
        this.girasolesPrimeraColumnaIniciales = estado.getGirasolesPrimeraColumnaIniciales();
        this.copiarFilasVisitadas(estado.getFilasVisitadas());
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
        return this.posicionesIguales(otroEstado.getPosicion())
                && this.energiaIgual(otroEstado.getEnergia())
                && this.filasVisitadasIguales(otroEstado.getFilasVisitadas())
                && planteGirasolIgual(otroEstado.getPlanteGirasol())
                && turnosIguales(otroEstado.getTurno())
                && this.deboMatarZombieIguales(otroEstado.getDeboMatarZombie())
                && this.costosIguales(otroEstado.getCosto())
                && this.meMoviIguales(otroEstado.getMeMovi())
                && this.mateZombieIguales(otroEstado.getMateZombie())
                && this.jardinesIguales(otroEstado.getJardin());

    }

    private boolean mateZombieIguales(boolean otroMateZombie) {
        return this.mateZombie == otroMateZombie;
    }

    private boolean posicionesIguales(Posicion otraPosicion) {
        return this.posicion.equals(otraPosicion);
    }

    private boolean energiaIgual(Integer otraEnergia) {
        return this.energia == otraEnergia;
    }

    private boolean planteGirasolIgual(Boolean otroPlanteGirasol) {
        return this.planteGirasol == otroPlanteGirasol;
    }

    private boolean turnosIguales(Integer otroTurno) {
        return this.turno == otroTurno;
    }

    private boolean deboMatarZombieIguales(Boolean otroDeboMatarZombie) {
        return this.deboMatarZombie == otroDeboMatarZombie;
    }

    private boolean costosIguales(Integer otroCosto) {
        return this.costo == otroCosto;
    }

    private boolean meMoviIguales(Boolean otroMeMovi) {
        return this.meMovi == otroMeMovi;
    }

    private boolean filasVisitadasIguales(Boolean[] otroFilasVisitadas) {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.filasVisitadas[fila] != otroFilasVisitadas[fila]) {
                return false;
            }
        }
        return true;
    }

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
        this.consumirPercepcionArriba(percepcion.getPercepcionArriba());
        this.consumirPercepcionAbajo(percepcion.getPercepcionAbajo());
        this.consumirPercepcionIzquierda(percepcion.getPercepcionIzquierda());
        this.consumirPercepcionDerecha(percepcion.getPercepcionDerecha());
        this.consumirPercepcion(percepcion.getPercepcionCentro());
        this.energia = percepcion.getEnergiaAgente();
        this.energiaInicial = percepcion.getEnergiaAgente();
        this.actualizarPercepcionesZombiesYSoles();
        this.planteGirasol = false;
        reiniciarFilasVisitadas();
        this.deboMatarZombie = this.zombiesEnElAmbiente > 0;
        this.costo = 0;
        this.meMovi = false;
        this.mateZombie = false;
        this.setGirasolesPrimeraColumnaIniciales(this.girasolesRestantesPrimerColumna());
    }

    private void consumirPercepcionArriba(PercepcionCasillero percepcion) {
        Posicion posicionAActualizar = new Posicion(posicion.fila - 1, posicion.columna);
        if (percepcion != null) {
            while (!percepcion.posicion.equals(posicionAActualizar)) {
                this.limpiarCasillerosArriba(posicionAActualizar);
            }
            this.consumirPercepcion(percepcion);
        } else {
            while (JardinEnvironmentState.posicionValida(posicionAActualizar)) {
                this.limpiarCasillerosArriba(posicionAActualizar);
            }
        }
    }

    private void limpiarCasillerosArriba(Posicion posicionAActualizar) {
        this.limpiarCasillero(posicionAActualizar);
        posicionAActualizar.fila--;
    }

    private void consumirPercepcionAbajo(PercepcionCasillero percepcion) {
        Posicion posicionAActualizar = new Posicion(posicion.fila + 1, posicion.columna);
        if (percepcion != null) {
            while (!percepcion.posicion.equals(posicionAActualizar)) {
                limpiarCasillerosAbajo(posicionAActualizar);
            }
            this.consumirPercepcion(percepcion);
        } else {
            while (JardinEnvironmentState.posicionValida(posicionAActualizar)) {
                limpiarCasillerosAbajo(posicionAActualizar);
            }
        }
    }

    private void limpiarCasillerosAbajo(Posicion posicionAActualizar) {
        this.limpiarCasillero(posicionAActualizar);
        posicionAActualizar.fila++;
    }

    private void consumirPercepcionIzquierda(PercepcionCasillero percepcion) {
        Posicion posicionAActualizar = new Posicion(posicion.fila, posicion.columna - 1);
        if (percepcion != null) {
            while (!percepcion.posicion.equals(posicionAActualizar)) {
                this.limpiarCasillerosIzquierda(posicionAActualizar);
            }
            this.consumirPercepcion(percepcion);
        } else {
            while (JardinEnvironmentState.posicionValida(posicionAActualizar)) {
                this.limpiarCasillerosIzquierda(posicionAActualizar);
            }
        }
    }

    private void limpiarCasillerosIzquierda(Posicion posicionAActualizar) {
        this.limpiarCasillero(posicionAActualizar);
        posicionAActualizar.columna--;
    }

    private void consumirPercepcionDerecha(PercepcionCasillero percepcion) {
        Posicion posicionAActualizar = new Posicion(posicion.fila, posicion.columna + 1);
        if (percepcion != null) {
            while (!percepcion.posicion.equals(posicionAActualizar)) {
                this.limpiarCasillerosDerecha(posicionAActualizar);
            }
            this.consumirPercepcion(percepcion);
            posicionAActualizar.columna++;
            while (JardinEnvironmentState.posicionValida(posicionAActualizar)) {
                this.limpiarCasillerosDerecha(posicionAActualizar);
            }
        } else {
            while (JardinEnvironmentState.posicionValida(posicionAActualizar)) {
                this.limpiarCasillerosDerecha(posicionAActualizar);
            }
        }
    }

    private void limpiarCasillerosDerecha(Posicion posicionAActualizar) {
        this.limpiarCasillero(posicionAActualizar);
        posicionAActualizar.columna++;
    }

    private void limpiarCasillero(Posicion posicionAActualizar) {
        this.jardin[posicionAActualizar.fila][posicionAActualizar.columna] = new Casillero();
    }

    private void consumirPercepcion(PercepcionCasillero percepcion) {
        this.jardin[percepcion.posicion.fila][percepcion.posicion.columna] = percepcion.casillero;
    }

    private void actualizarPercepcionesZombiesYSoles() {
        Integer cantidadZombies = 0;
        Integer vidaZombies = 0;
        Integer solesPercibidos = 0;
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                if (this.jardin[fila][columna].zombie != null) {
                    cantidadZombies++;
                    vidaZombies += this.jardin[fila][columna].zombie.vida;
                }
                solesPercibidos += this.jardin[fila][columna].cantidadDeSoles;
            }
        }
        this.zombiesEnElAmbiente = cantidadZombies;
        this.vidaZombiesPercibidos = vidaZombies;
    }

    private void reiniciarFilasVisitadas() {
        if (this.todasLasFilasVisitadas()) {
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
        result += "Suma vida zombies: " + this.vidaZombiesPercibidos + "\n";
        result += "Filas visitadas: " + "\n";
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.filasVisitadas[fila]) {
                result += "T ";
            } else {
                result += "F ";
            }
        }
        result += "\nTurno: " + this.turno + "\n";
        result += "Plante girasol: " + this.planteGirasol.toString() + "\n";
        result += "\n";
        result += "Heuristica: " + this.zombiesRestantesPorMatarHeuristica() + " - "
                + this.todasLasFilasVisitadasHeuristica() + " - "
                + this.girasolesRestantesPrimerColumna() + "\n";
        result += "\n";
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

    public Boolean getPlanteGirasol() {
        return this.planteGirasol;
    }

    public void setPlanteGirasol(Boolean planteGirasol) {
        this.planteGirasol = planteGirasol;
    }

    public Integer getZombiesPorMatar() {
        return this.zombiesEnElAmbiente;
    }

    public Integer getVidaZombiesPercibidos() {
        return this.vidaZombiesPercibidos;
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
        this.mateZombie = true;
    }

    private Casillero getCasilleroActual() {
        return this.jardin[this.posicion.fila][this.posicion.columna];
    }

    public Integer getTurno() {
        return turno;
    }

    public void sumarTurno() {
        this.turno++;
    }

    public Boolean getDeboMatarZombie() {
        return this.deboMatarZombie;
    }

    public Boolean getMeMovi() {
        return this.meMovi;
    }

    public void setMeMovi(Boolean meMovi) {
        this.meMovi = meMovi;
    }

    public Integer getCosto() {
        return costo;
    }

    public void agregarCosto(Integer costoAAgregar) {
        this.costo += costoAAgregar;
    }

    public Integer getSolesEnPosicion(Posicion posicionAExaminar) {
        return this.jardin[posicionAExaminar.fila][posicionAExaminar.columna].cantidadDeSoles;
    }

    // BUSQUEDA EN AMPLITUDD
    // public Boolean objetivoCumplido() {
    // Boolean objetivoVariable = false;
    // if (this.energia > this.vidaZombiesPercibidos && this.energia > 10 &&
    // this.turno > 5) {
    // if (this.deboMatarZombie) {
    // objetivoVariable = this.zombiesEnElAmbiente == 0;
    // } else {
    // objetivoVariable = this.todasLasFilasVisitadas();
    // }
    // } else {
    // objetivoVariable = this.planteGirasol;
    // }
    // return this.energia > 0 && objetivoVariable;
    // }

    // BUSQUEDA POR COSTO
    // public Boolean objetivoCumplido() {
    // Boolean objetivoVariable = false;
    // if (this.deboMatarZombie && turno > 11) {
    // objetivoVariable = this.zombiesEnElAmbiente == 0;
    // } else if (turno < 11) {
    // objetivoVariable = this.planteGirasol;
    // } else {
    // objetivoVariable = this.meMovi;
    // }
    // return this.energia > 0 && objetivoVariable;
    // }

    // BUSQUEDA AVARA
    public Boolean objetivoCumplido() {
        Boolean heuristicaCero = (this.girasolesRestantesPrimerColumna() +
                this.todasLasFilasVisitadasHeuristica()
                + this.zombiesRestantesPorMatarHeuristica()) == 0;
        return this.energia > 0 && heuristicaCero;
    }

    public Integer girasolesRestantesPrimerColumna() {
        Integer girasolesRestantes = 5;
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.jardin[fila][JardinEnvironmentState.PRIMERA_COLUMNA].girasol != null)
                girasolesRestantes--;
        }
        return girasolesRestantes * 3;
    }

    public Integer todasLasFilasVisitadasHeuristica() {
        if (this.girasolesPrimeraColumnaIniciales > 0 || this.deboMatarZombie) {
            return 0;
        }
        Integer filasPorVisitar = 5;
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (this.filasVisitadas[fila])
                filasPorVisitar--;
        }
        return filasPorVisitar * 1;
    }

    public Integer zombiesRestantesPorMatarHeuristica() {
        if (this.girasolesPrimeraColumnaIniciales > 0) {
            return 0;
        } else {
            Posicion posicionZombie = this.getPosicionZombieMasCercanoALaCasa();
            if (posicionZombie == null) {
                return 0;
            }
            if (posicionZombie.equals(this.posicion)) {
                return this.zombiesEnElAmbiente;
            } else {
                return Math.abs(Math.abs(posicionZombie.columna - this.posicion.columna) * 2
                        + Math.abs(posicionZombie.fila - this.posicion.fila)) * (this.mateZombie ? 0 : 1);
            }
        }
    }

    private Posicion getPosicionZombieMasCercanoALaCasa() {
        for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
            for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
                if (this.jardin[fila][columna].zombie != null) {
                    return new Posicion(fila, columna);
                }
            }
        }
        return null;
    }

    private Boolean todasLasFilasVisitadas() {
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            if (!this.filasVisitadas[fila])
                return false;
        }
        return true;
    }

    public Integer getEnergiaInicial() {
        return energiaInicial;
    }

    public Integer getGirasolesPrimeraColumnaIniciales() {
        return girasolesPrimeraColumnaIniciales;
    }

    public void setGirasolesPrimeraColumnaIniciales(Integer girasolesPrimeraColumnaIniciales) {
        this.girasolesPrimeraColumnaIniciales = girasolesPrimeraColumnaIniciales;
    }

    public void setEnergiaInicial(Integer energiaInicial) {
        this.energiaInicial = energiaInicial;
    }

    public boolean getMateZombie() {
        return mateZombie;
    }

    public void setMateZombie(boolean mateZombie) {
        this.mateZombie = mateZombie;
    }

}
