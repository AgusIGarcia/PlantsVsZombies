package frsf.cidisi.faia.search.plants_vs_zombies.ambiente;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Casillero;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.Zombie;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.Girasol;

import frsf.cidisi.faia.state.EnvironmentState;

public class JardinEnvironmentState extends EnvironmentState {

    public static final Integer FILAS_JARDIN = 5;
    public static final Integer COLUMNAS_JARDIN = 9;

    public static final Integer PRIMERA_FILA = 0;
    public static final Integer ULTIMA_FILA = 4;

    public static final Integer PRIMERA_COLUMNA = 0;
    public static final Integer ULTIMA_COLUMNA = 8;

    private final InicioJuego parametrosInicio;
    private final UltimaColumna ultimaColumna;

    private Casillero[][] jardin;
    private Posicion posicionRepollo;
    private Integer energiaRepollo;
    private Integer cantidadZombiesAGenerar;
    private Integer zombiesEnJuego;
    private Boolean zombieLlego;

    public JardinEnvironmentState(InicioJuego parametrosInicio) {
        this.parametrosInicio = parametrosInicio;
        this.ultimaColumna = new UltimaColumna(FILAS_JARDIN);
        this.initState();
    }

    @Override
    public void initState() {
        this.inicializarJardin();
        this.posicionRepollo = this.parametrosInicio.posicionRepollo;
        this.energiaRepollo = this.parametrosInicio.energiaRepollo;
        this.cantidadZombiesAGenerar = this.parametrosInicio.cantidadZombiesAGenerar;
        this.zombiesEnJuego = 0;
        this.zombieLlego = false;
    }

    private void inicializarJardin() {
        this.jardin = new Casillero[FILAS_JARDIN][COLUMNAS_JARDIN];
        for (int fila = PRIMERA_FILA; fila <= ULTIMA_FILA; fila++) {
            for (int columna = PRIMERA_COLUMNA; columna <= ULTIMA_COLUMNA; columna++) {
                this.jardin[fila][columna] = new Casillero();
            }
        }
    }

    private Boolean insertarZombie() {
        if (this.ultimaColumna.puedoInsertarZombie() && RandomPropio.generarNumeroRandom(1, 3) == 2) {
            Integer filaAInsertarZombie = this.ultimaColumna.insertarZombie();
            this.jardin[filaAInsertarZombie][ULTIMA_COLUMNA].zombie = new Zombie();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "Vista jardin ambiente: \n";
        result += "------------------------------------------------------------------------------------------------------------------";
        result += "\n";
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                if (this.posicionRepollo.fila == fila && this.posicionRepollo.columna == columna) {
                    result += "(P," + this.jardin[fila][columna].toString() + ")";
                } else {
                    result += "(_," + this.jardin[fila][columna].toString() + ")";
                }
            }
            result += "\n";
        }
        result += "------------------------------------------------------------------------------------------------------------------";
        result += "\n";
        result += "Energía repollo: " + this.energiaRepollo + "\n";
        result += "Zombies por generar: " + this.cantidadZombiesAGenerar + "\n";
        result += "Zombies en ambiente: " + this.zombiesEnJuego + "\n";
        result += "Posiciones Disponibles: " + "\n";
        result += this.ultimaColumna.toString();
        result += "\n";
        return result;
    }

    public static Boolean posicionValida(Posicion posicion) {
        return posicion.fila >= PRIMERA_FILA && posicion.fila <= ULTIMA_FILA && posicion.columna >= PRIMERA_COLUMNA
                && posicion.columna <= ULTIMA_COLUMNA;
    }

    public void cicloPercepcion() {
        recorrerMapaYActualizar();
    }

    private void recorrerMapaYActualizar() {
        for (int fila = PRIMERA_FILA; fila <= ULTIMA_FILA; fila++) {
            for (int columna = PRIMERA_COLUMNA; columna <= ULTIMA_COLUMNA; columna++) {
                Posicion posicionActual = new Posicion(fila, columna);
                this.generarSoles(posicionActual);
                this.moverZombies(posicionActual);
            }
        }
        this.ubicarZombie();
    }

    private void generarSoles(Posicion posicionActual) {
        Casillero casilleroActual = this.jardin[posicionActual.fila][posicionActual.columna];
        Girasol girasol = casilleroActual.girasol;
        if (girasol != null) {
            girasol.ocurrioCiclo();
            casilleroActual.cantidadDeSoles += girasol.recolectarSoles();
        }
    }

    private void moverZombies(Posicion posicionActual) {
        Casillero casilleroActual = this.jardin[posicionActual.fila][posicionActual.columna];
        Zombie zombie = casilleroActual.zombie;
        if (zombie != null) {
            zombie.ocurrioCiclo();
            if (zombie.puedeAvanzar() && !this.hayZombieDelante(posicionActual)) {
                this.avanzarZombie(casilleroActual, posicionActual);
            }
        }
    }

    private void avanzarZombie(Casillero casilleroActual, Posicion posicionActual) {
        Zombie zombie = casilleroActual.zombie;
        zombie.avanzar();
        casilleroActual.zombie = null;
        if (posicionActual.columna == PRIMERA_COLUMNA) {
            this.zombieLlego = true;
        } else {
            if (posicionActual.columna == ULTIMA_COLUMNA) {
                this.ultimaColumna.posicionLiberada(posicionActual.fila);
            }
            Posicion nuevaPosicion = new Posicion(posicionActual.fila, posicionActual.columna - 1);
            this.jardin[nuevaPosicion.fila][nuevaPosicion.columna].zombie = zombie;
            this.jardin[nuevaPosicion.fila][nuevaPosicion.columna].girasol = null;
            this.zombieAtacaAgenteSiPuede(zombie, nuevaPosicion);
        }
    }

    private void zombieAtacaAgenteSiPuede(Zombie zombie, Posicion nuevaPosicion) {
        if (this.posicionRepollo.equals(nuevaPosicion)) {
            this.energiaRepollo -= zombie.danioAlAgente();
        }
    }

    private Boolean hayZombieDelante(Posicion posicion) {
        Posicion posicionDelante = new Posicion(posicion.fila, posicion.columna - 1);
        return posicionValida(posicionDelante)
                && this.jardin[posicionDelante.fila][posicionDelante.columna].zombie != null;
    }

    private void ubicarZombie() {
        if (this.cantidadZombiesAGenerar > 0) {
            determinarSiSeUbicaZombie();
        }
    }

    private void determinarSiSeUbicaZombie() {
        Integer probabilidadGenerarZombie = RandomPropio.generarNumeroRandom(0, 1);
        if (probabilidadGenerarZombie == 1) {
            probarInsertarZombie();
        }
    }

    private void probarInsertarZombie() {
        if (this.insertarZombie()) {
            this.cantidadZombiesAGenerar--;
            this.zombiesEnJuego++;
        }
    }

    public Boolean pruebaDeFallo() {
        return this.zombieLlego || this.energiaRepollo <= 0;
    }

    public boolean winAgente() {
        return this.getCantidadZombiesRestantes() == 0 && !pruebaDeFallo();
    }

    public Posicion getPosicionRepollo() {
        return this.posicionRepollo;
    }

    public void setPosicionRepollo(Posicion posicionDestino) {
        this.posicionRepollo = posicionDestino;
    }

    public Casillero[][] getJardin() {
        return this.jardin;
    }

    public Integer getZombiesEnJuego() {
        return this.zombiesEnJuego;
    }

    public Integer getEnergiaRepollo() {
        return this.energiaRepollo;
    }

    public void repolloRecolectaSoles() {
        Casillero casilleroActual = getCasilleroRepollo();
        this.energiaRepollo += casilleroActual.cantidadDeSoles;
        casilleroActual.cantidadDeSoles = 0;
    }

    public Boolean sePuedePlantarGirasol() {
        return this.getCasilleroRepollo().girasol == null && this.getCasilleroRepollo().zombie == null
                && this.energiaRepollo > 0;
    }

    public void plantarGirasol() {
        this.getCasilleroRepollo().girasol = new Girasol();
        this.energiaRepollo--;
    }

    public void repolloPierdeVidaPostAvanzar() {
        Casillero casilleroActual = getCasilleroRepollo();
        if (casilleroActual.zombie != null) {
            this.energiaRepollo -= casilleroActual.zombie.danioAlAgente();
        }
    }

    public Boolean repolloPuedeMatarZombie(Posicion posicionAAtacar) {
        Casillero casilleroAAtacar = this.jardin[posicionAAtacar.fila][posicionAAtacar.columna];
        return casilleroAAtacar.zombie != null && this.energiaRepollo > (casilleroAAtacar.zombie.vida);
    }

    public void matarZombie(Posicion posicionAAtacar) {
        Casillero casilleroAAtacar = this.jardin[posicionAAtacar.fila][posicionAAtacar.columna];
        this.energiaRepollo -= casilleroAAtacar.zombie.vida;
        casilleroAAtacar.zombie = null;
        this.zombiesEnJuego--;
    }

    private Casillero getCasilleroRepollo() {
        return this.jardin[this.posicionRepollo.fila][this.posicionRepollo.columna];
    }

    public Integer getCantidadZombiesRestantes() {
        return this.zombiesEnJuego + this.cantidadZombiesAGenerar;
    }
}
