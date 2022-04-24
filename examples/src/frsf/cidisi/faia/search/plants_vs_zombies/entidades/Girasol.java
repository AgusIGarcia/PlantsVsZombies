package frsf.cidisi.faia.search.plants_vs_zombies.entidades;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;

public class Girasol {

    private static final Integer CANTIDAD_MINIMA_DE_SOLES_A_GENERAR = 1;
    private static final Integer CANTIDAD_MAXIMA_DE_SOLES_A_GENERAR = 3;

    private Integer solesGenerados;

    public Girasol() {
        this.solesGenerados = 0;
    }

    public Girasol(Girasol otroGirasol){
        this.solesGenerados = otroGirasol.getSolesGenerados();
    }

    public void ocurrioCiclo() {
        this.solesGenerados = RandomPropio.generarNumeroRandom(CANTIDAD_MINIMA_DE_SOLES_A_GENERAR,
                CANTIDAD_MAXIMA_DE_SOLES_A_GENERAR);
    }

    public Integer recolectarSoles() {
        return this.solesGenerados;
    }

    public Integer getSolesGenerados(){
        return this.solesGenerados;
    }
}
