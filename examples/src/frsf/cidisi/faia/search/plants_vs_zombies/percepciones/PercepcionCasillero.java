package frsf.cidisi.faia.search.plants_vs_zombies.percepciones;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Casillero;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;

public class PercepcionCasillero {
    public Casillero casillero;
    public Posicion posicion;

    public PercepcionCasillero(Casillero casillero, Posicion posicion) {
        this.casillero = casillero;
        this.posicion = posicion;
    }
}
