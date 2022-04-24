package frsf.cidisi.faia.search.plants_vs_zombies.percepciones;

import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironment;

public class RepolloBoxeadorPerception extends Perception {

    private PercepcionCasillero percepcionArriba;
    private PercepcionCasillero percepcionAbajo;
    private PercepcionCasillero percepcionCentro;
    private PercepcionCasillero percepcionIzquierda;
    private PercepcionCasillero percepcionDerecha;
    private Integer cantidadZombiesRestantes;
    private Integer energiaAgente;

    @Override
    public void initPerception(Agent agent, Environment environment) {
        JardinEnvironment jardin = (JardinEnvironment) environment;
        this.setValores((RepolloBoxeadorPerception) jardin.getPercept());
    }

    private void setValores(RepolloBoxeadorPerception percept) {
        this.percepcionArriba = percept.getPercepcionArriba();
        this.percepcionAbajo = percept.getPercepcionAbajo();
        this.percepcionCentro = percept.getPercepcionCentro();
        this.percepcionIzquierda = percept.getPercepcionIzquierda();
        this.percepcionDerecha = percept.getPercepcionDerecha();
        this.cantidadZombiesRestantes = percept.getCantidadZombiesRestantes();
        this.energiaAgente = percept.getEnergiaAgente();
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

    public Integer getCantidadZombiesRestantes() {
        return cantidadZombiesRestantes;
    }

    public void setCantidadZombiesRestantes(Integer cantidadZombiesAmbiente) {
        this.cantidadZombiesRestantes = cantidadZombiesAmbiente;
    }

    public Integer getEnergiaAgente() {
        return energiaAgente;
    }

    public void setEnergiaAgente(Integer energiaAgente) {
        this.energiaAgente = energiaAgente;
    }

}
