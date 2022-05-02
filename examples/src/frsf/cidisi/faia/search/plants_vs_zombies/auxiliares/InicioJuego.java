package frsf.cidisi.faia.search.plants_vs_zombies.auxiliares;

import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;

public class InicioJuego {

    private static final Integer CANTIDAD_MINIMA_DE_ENERGIA_INICIAL = 2;
    private static final Integer CANTIDAD_MAXIMA_DE_ENERGIA_INICIAL = 20;

    private static final Integer CANTIDAD_MINIMA_DE_ZOMBIES_TOTALES = 1;
    private static final Integer CANTIDAD_MAXIMA_DE_ZOMBIES_TOTALES = 1;
    
    public Posicion posicionRepollo;
    public Integer energiaRepollo;

    public Integer cantidadZombiesAGenerar;

    public InicioJuego() {
        this.posicionRepollo = 
        new Posicion(RandomPropio.generarNumeroRandom(JardinEnvironmentState.PRIMERA_FILA, JardinEnvironmentState.ULTIMA_FILA), JardinEnvironmentState.PRIMERA_COLUMNA);
        this.energiaRepollo = RandomPropio.generarNumeroRandom(CANTIDAD_MINIMA_DE_ENERGIA_INICIAL, CANTIDAD_MAXIMA_DE_ENERGIA_INICIAL);
        this.cantidadZombiesAGenerar = 
        RandomPropio.generarNumeroRandom(CANTIDAD_MINIMA_DE_ZOMBIES_TOTALES, CANTIDAD_MAXIMA_DE_ZOMBIES_TOTALES);
    }

}
