package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MoverDerecha extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        Posicion posicionDestino = calcularPosicionDestino(repolloState.getPosicion());

        return new MoverHelperAgente(posicionDestino, repolloState).execute();
    }

    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        Posicion posicionDestino = calcularPosicionDestino(jardinState.getPosicionRepollo());

        return new MoverHelperAmbiente(posicionDestino, jardinState).execute();
    }

    private Posicion calcularPosicionDestino(Posicion posicionActual) {
        Posicion posicionDestino = new Posicion(posicionActual.fila + 1, posicionActual.columna);
        return posicionDestino;
    }

    @Override
    public Double getCost() {
        return 0d;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}