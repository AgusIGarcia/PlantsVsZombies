package frsf.cidisi.faia.search.plants_vs_zombies.ambiente;

import java.util.ArrayList;
import java.util.List;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;

public class UltimaColumna {

    private List<Boolean> posicionesOcupadas;
    private Integer cantidadFilas;

    public UltimaColumna(Integer cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
        this.posicionesOcupadas = new ArrayList<Boolean>();
        this.inicializarListas();
    }

    private void inicializarListas() {
        for (Integer i = 0; i < this.cantidadFilas; i++) {
            this.posicionesOcupadas.add(false);
        }
    }

    public Boolean puedoInsertarZombie() {
        return posicionesOcupadas.contains(false);
    }

    public Integer insertarZombie() {
        List<Integer> listaPosicionesDisponibles = new ArrayList<>();
        for (Integer i = 0; i < this.cantidadFilas; i++) {
            if (!this.posicionesOcupadas.get(i)) {
                listaPosicionesDisponibles.add(i);
            }
        }
        Integer posicionAleatoria = RandomPropio.generarNumeroRandom(0, listaPosicionesDisponibles.size() - 1);
        Integer posicionFinal = listaPosicionesDisponibles.get(posicionAleatoria);
        this.posicionesOcupadas.set(posicionFinal, true);
        return posicionFinal;
    }

    public void posicionLiberada(Integer fila) {
        this.posicionesOcupadas.set(fila, false);
    }

    @Override
    public String toString() {
        String result = "";
        for (int fila = 0; fila < cantidadFilas; fila++) {
            if (this.posicionesOcupadas.get(fila)) {
                result += "F ";
            } else {
                result += "T ";
            }
        }
        return result;
    }
}
