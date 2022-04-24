package frsf.cidisi.faia.search.plants_vs_zombies.auxiliares;

public class Posicion {
    public Integer fila;
    public Integer columna;

    public Posicion(Integer fila, Integer columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public boolean equals(Posicion otraPosicion){
        return this.fila == otraPosicion.fila && this.columna == otraPosicion.columna;
    }
}
