package frsf.cidisi.faia.search.plants_vs_zombies.ambiente;

import java.util.ArrayList;
import java.util.List;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;

public class UltimaColumna {

    private List<Boolean> posicionesOcupadas;
    private Integer cantidadFilas;
    private List<Integer> posicionesDisponibles;

    public UltimaColumna(Integer cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
        this.posicionesOcupadas = new ArrayList<Boolean>();
        this.inicializarListas();
    }

    private void inicializarListas(){
        for(Integer i = 0 ; i < this.cantidadFilas ; i++){
            this.posicionesOcupadas.add(false);
            this.posicionesDisponibles.add(i);
        }
    }

    public Boolean puedoInsertarZombie(){
        return posicionesOcupadas.contains(false);
    }

    public Integer insertarZombie(){
        Integer posicionAOcupar = RandomPropio.generarNumeroRandom(0, this.posicionesDisponibles.size() - 1);
        Integer posicionFinalZombie = this.posicionesDisponibles.get(posicionAOcupar);
        this.posicionesOcupadas.set(posicionFinalZombie,true);
        this.posicionesDisponibles.remove(posicionAOcupar);
        return posicionFinalZombie;
    }

    public void posicionLiberada(Integer fila){
        this.posicionesOcupadas.set(fila,false);
        this.posicionesDisponibles.add(fila);
    }

}
