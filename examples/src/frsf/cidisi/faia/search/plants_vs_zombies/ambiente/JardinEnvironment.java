package frsf.cidisi.faia.search.plants_vs_zombies.ambiente;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Casillero;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.PercepcionCasillero;
import frsf.cidisi.faia.search.plants_vs_zombies.percepciones.RepolloBoxeadorPerception;

public class JardinEnvironment extends Environment {
    public JardinEnvironment(InicioJuego parametrosInicio) {
        super();
        this.environmentState = new JardinEnvironmentState(parametrosInicio);
    }

    @Override
    public JardinEnvironmentState getEnvironmentState() {
        return (JardinEnvironmentState) this.environmentState;
    }

    @Override
    public Perception getPercept() {

        this.getEnvironmentState().cicloPercepcion();
        RepolloBoxeadorPerception perception = new RepolloBoxeadorPerception();
        setPercepciones(perception);

        return perception;
    }

    @Override
    public boolean agentFailed(Action actionReturned) {
        return this.getEnvironmentState().pruebaDeFallo();
    }

    public boolean esWinAgente(){
        return this.getEnvironmentState().winAgente();
    }
    

    private void setPercepciones(RepolloBoxeadorPerception perception) {

        Posicion posicionRepollo = this.getEnvironmentState().getPosicionRepollo();
        Casillero[][] jardin = this.getEnvironmentState().getJardin();

        perception.setPercepcionArriba(this.getPercepcionArriba(posicionRepollo, jardin));
        perception.setPercepcionAbajo(this.getPercepcionAbajo(posicionRepollo, jardin));
        perception.setPercepcionCentro(this.getPercepcionCentro(posicionRepollo, jardin));
        perception.setPercepcionIzquierda(this.getPercepcionIzquierda(posicionRepollo, jardin));
        perception.setPercepcionDerecha(this.getPercepcionDerecha(posicionRepollo, jardin));
        perception.setEnergiaAgente(this.getEnvironmentState().getEnergiaRepollo());
    }

    private PercepcionCasillero getPercepcionArriba(Posicion posicionRepollo, Casillero[][] jardin) {
        PercepcionCasillero percepcion = null;
        Posicion posicionAChequear = new Posicion(posicionRepollo.fila + 1, posicionRepollo.columna);
        while (percepcion == null && JardinEnvironmentState.posicionValida(posicionAChequear)) {
            Casillero casilleroAChequear = jardin[posicionAChequear.fila][posicionAChequear.columna];
            if (casilleroAChequear.tengoContenido()) {
                percepcion = new PercepcionCasillero(casilleroAChequear, posicionAChequear);
            } else {
                posicionAChequear.fila++; 
            }
        }
        return percepcion;
    }

    private PercepcionCasillero getPercepcionAbajo(Posicion posicionRepollo, Casillero[][] jardin) {
        PercepcionCasillero percepcion = null;
        Posicion posicionAChequear = new Posicion(posicionRepollo.fila - 1, posicionRepollo.columna);
        while (percepcion == null && JardinEnvironmentState.posicionValida(posicionAChequear)) {
            Casillero casilleroAChequear = jardin[posicionAChequear.fila][posicionAChequear.columna];
            if (casilleroAChequear.tengoContenido()) {
                percepcion = new PercepcionCasillero(casilleroAChequear, posicionAChequear);
            } else {
                posicionAChequear.fila--; 
            }
        }
        return percepcion;
    }

    private PercepcionCasillero getPercepcionIzquierda(Posicion posicionRepollo, Casillero[][] jardin) {
        PercepcionCasillero percepcion = null;
        Posicion posicionAChequear = new Posicion(posicionRepollo.fila, posicionRepollo.columna - 1);
        while (percepcion == null && JardinEnvironmentState.posicionValida(posicionAChequear)) {
            Casillero casilleroAChequear = jardin[posicionAChequear.fila][posicionAChequear.columna];
            if (casilleroAChequear.tengoContenido()) {
                percepcion = new PercepcionCasillero(casilleroAChequear, posicionAChequear);
            } else {
                posicionAChequear.columna--; 
            }
        }
        return percepcion;
    }

    private PercepcionCasillero getPercepcionDerecha(Posicion posicionRepollo, Casillero[][] jardin) {
        PercepcionCasillero percepcion = null;
        Posicion posicionAChequear = new Posicion(posicionRepollo.fila, posicionRepollo.columna + 1);
        while (percepcion == null && JardinEnvironmentState.posicionValida(posicionAChequear)) {
            Casillero casilleroAChequear = jardin[posicionAChequear.fila][posicionAChequear.columna];
            if (casilleroAChequear.tengoContenido()) {
                percepcion = new PercepcionCasillero(casilleroAChequear, posicionAChequear);
            } else {
                posicionAChequear.columna++; 
            }
        }
        return percepcion;
    }

    private PercepcionCasillero getPercepcionCentro(Posicion posicionRepollo, Casillero[][] jardin) {
        PercepcionCasillero percepcion = new PercepcionCasillero(jardin[posicionRepollo.fila][posicionRepollo.columna], posicionRepollo);
        return percepcion;
    }

}
