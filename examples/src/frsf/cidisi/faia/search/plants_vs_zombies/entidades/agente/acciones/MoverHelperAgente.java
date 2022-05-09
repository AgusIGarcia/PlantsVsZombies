package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;

public class MoverHelperAgente {

    private Posicion posicionDestino;
    private RepolloBoxeadorAgentState repolloState;
    private Integer costoPorDefecto;
    private Posicion posicionAnterior;

    public MoverHelperAgente(Posicion posicionDestino, RepolloBoxeadorAgentState repolloState,
            Integer costoPorDefecto) {
        this.posicionDestino = posicionDestino;
        this.repolloState = repolloState;
        this.costoPorDefecto = costoPorDefecto;
        this.posicionAnterior = repolloState.getPosicion();
    }

    public RepolloBoxeadorAgentState execute() {

        if (JardinEnvironmentState.posicionValida(posicionDestino) && this.repolloState.getEnergia() > 0) {
            preMovimiento();
            actualizarPosicionAgente();
            postMovimiento();
            return this.repolloState;
        }

        return null;
    }

    public void preMovimiento() {
        this.repolloState.recolectarSoles();
    }

    public void actualizarPosicionAgente() {
        this.repolloState.setPosicion(this.posicionDestino);
    }

    private void postMovimiento() {
        if (this.repolloState.getDeboMatarZombie()) {
            if (this.posicionAnterior.columna < this.posicionDestino.columna)
                this.costoPorDefecto = 150;
            else
                this.costoPorDefecto = 100;
        } else {
            this.costoPorDefecto = this.costoPorDefecto - this.repolloState.getSolesEnPosicion(posicionDestino);
        }
        this.repolloState.agregarCosto(this.costoPorDefecto);
        this.repolloState.recolectarSoles();
        this.repolloState.perderEnergiaPorZombie();
        this.repolloState.actualizarFilasVisitadas();
        this.repolloState.sumarTurno();
        this.repolloState.setMeMovi(true);
    }
}