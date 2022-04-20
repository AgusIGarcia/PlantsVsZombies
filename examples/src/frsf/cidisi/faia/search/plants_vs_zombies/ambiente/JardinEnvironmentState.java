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
    private Integer cantidadZombiesInicial;
    private Integer zombiesEnJuego;
    private Boolean zombieLlego;

    public JardinEnvironmentState(InicioJuego parametrosInicio) {
        this.jardin = new Casillero[FILAS_JARDIN][COLUMNAS_JARDIN];
        this.parametrosInicio = parametrosInicio;
        this.ultimaColumna = new UltimaColumna(FILAS_JARDIN);
    }

    @Override
    public void initState() {
        this.inicializarVariables();
        //Mejorable
        this.ubicarZombiesIniciales();
    }

    private void inicializarVariables() {
        this.posicionRepollo = this.parametrosInicio.posicionRepollo;
        this.energiaRepollo = this.parametrosInicio.energiaRepollo;
        this.cantidadZombiesInicial = this.parametrosInicio.cantidadZombiesInicial;
        this.cantidadZombiesAGenerar = this.parametrosInicio.cantidadZombiesAGenerar;
        this.zombiesEnJuego = this.cantidadZombiesInicial;
        this.zombieLlego = false;
    }

    private void ubicarZombiesIniciales() {
        for (int i = 0; i < this.cantidadZombiesInicial; i++) {
            this.insertarZombie();
        }
    }

    private Boolean insertarZombie() {
        if (this.ultimaColumna.puedoInsertarZombie()) {
            Integer filaAInsertarZombie = this.ultimaColumna.insertarZombie();
            this.jardin[filaAInsertarZombie][ULTIMA_COLUMNA].zombie = new Zombie();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    public Boolean posicionValida(Posicion posicion) {
        return posicion.fila >= PRIMERA_FILA && posicion.fila <= ULTIMA_FILA && posicion.columna >= PRIMERA_COLUMNA
                && posicion.columna < ULTIMA_COLUMNA;
    }

    public void clicloPercepcion() {
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
        Girasol girasol = casilleroActual.getGirasol();
        if (girasol != null) {
            girasol.ocurrioCiclo();
            casilleroActual.cantidadDeSoles += girasol.recolectarSoles();
        }
    }

    private void moverZombies(Posicion posicionActual) {
        Casillero casilleroActual = this.jardin[posicionActual.fila][posicionActual.columna];
        Zombie zombie = casilleroActual.getZombie();
        if (zombie != null) {
            zombie.ocurrioCiclo();
            if (zombie.puedeAvanzar()) {
                this.avanzarZombie(casilleroActual, posicionActual);
            }
        }
    }

    private void avanzarZombie(Casillero casilleroActual, Posicion posicionActual) {
        Zombie zombie = casilleroActual.getZombie();
        zombie.avanzar();
        casilleroActual.setZombie(null);
        //REVISAR SI HAY GIRASOL O ZOMBIE, FALTÓ
        if (posicionActual.columna == PRIMERA_COLUMNA) {
            this.zombieLlego = true;
        } else {
            this.jardin[posicionActual.fila][posicionActual.columna - 1].setZombie(zombie);
        }
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
        }
    }

    public Boolean pruebaDeFallo() {
        return this.zombieLlego || this.energiaRepollo <= 0;
    }

    public Posicion getPosicionRepollo() {
        return this.posicionRepollo;
    }

    public Casillero[][] getJardin() {
        return this.jardin;
    }

    public Integer getZombiesEnJuego() {
        return this.zombiesEnJuego;
    }
}
