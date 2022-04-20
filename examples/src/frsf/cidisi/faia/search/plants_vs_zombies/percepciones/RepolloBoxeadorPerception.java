package frsf.cidisi.faia.search.plants_vs_zombies.percepciones;

import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;

public class RepolloBoxeadorPerception extends Perception {

    private PercepcionCasillero percepcionArriba;
    private PercepcionCasillero percepcionAbajo;
    private PercepcionCasillero percepcionCentro;
    private PercepcionCasillero percepcionIzquierda;
    private PercepcionCasillero percepcionDerecha;
    private Integer cantidadZombiesAmbiente;

    @Override
    public void initPerception(Agent agent, Environment environment) {
        // TODO Auto-generated method stub

    }

    public PercepcionCasillero getPercepcionArriba() {
        return percepcionArriba;
    }

    public void setPercepcionArriba(PercepcionCasillero percepcionArriba) {
        this.percepcionArriba = percepcionArriba;
    }

    public PercepcionCasillero getPercepcionAbajo() {
        return percepcionAbajo;
    }

    public void setPercepcionAbajo(PercepcionCasillero percepcionAbajo) {
        this.percepcionAbajo = percepcionAbajo;
    }

    public PercepcionCasillero getPercepcionCentro() {
        return percepcionCentro;
    }

    public void setPercepcionCentro(PercepcionCasillero percepcionCentro) {
        this.percepcionCentro = percepcionCentro;
    }

    public PercepcionCasillero getPercepcionIzquierda() {
        return percepcionIzquierda;
    }

    public void setPercepcionIzquierda(PercepcionCasillero percepcionIzquierda) {
        this.percepcionIzquierda = percepcionIzquierda;
    }

    public PercepcionCasillero getPercepcionDerecha() {
        return percepcionDerecha;
    }

    public void setPercepcionDerecha(PercepcionCasillero percepcionDerecha) {
        this.percepcionDerecha = percepcionDerecha;
    }

    public Integer getCantidadZombiesAmbiente() {
        return cantidadZombiesAmbiente;
    }

    public void setCantidadZombiesAmbiente(Integer cantidadZombiesAmbiente) {
        this.cantidadZombiesAmbiente = cantidadZombiesAmbiente;
    }

}
