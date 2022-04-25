package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;

public class MoverHelperAgente {

    private Posicion posicionDestino;
    private RepolloBoxeadorAgentState repolloState;

    public MoverHelperAgente(Posicion posicionDestino, RepolloBoxeadorAgentState repolloState) {
        this.posicionDestino = posicionDestino;
        this.repolloState = repolloState;
    }

    public RepolloBoxeadorAgentState execute(){
        
        if (JardinEnvironmentState.posicionValida(posicionDestino)) {
            preMovimiento();
            actualizarPosicionAgente();
            postMovimiento();
        }

        return this.repolloState;
    }

    public void preMovimiento(){
        this.repolloState.recolectarSoles();
    }

    public void actualizarPosicionAgente() {
        this.repolloState.setPosicion(this.posicionDestino);
    }


    private void postMovimiento() {
        this.repolloState.recolectarSoles();
        this.repolloState.perderEnergiaPorZombie();
    }
}