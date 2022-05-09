package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MoverArriba extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        Posicion posicionDestino = calcularPosicionDestino(repolloState.getPosicion());

        return new MoverHelperAgente(posicionDestino, repolloState, 1000).execute();
    }

    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) ast;
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        Posicion posicionDestino = calcularPosicionDestino(jardinState.getPosicionRepollo());

        JardinEnvironmentState moverHelperAmbiente = new MoverHelperAmbiente(posicionDestino, jardinState).execute();

        if (moverHelperAmbiente != null){
            new MoverHelperAgente(posicionDestino, repolloState, 1000).execute();
        }
        
        return moverHelperAmbiente;
    }

    private Posicion calcularPosicionDestino(Posicion posicionActual) {
        Posicion posicionDestino = new Posicion(posicionActual.fila - 1, posicionActual.columna);
        return posicionDestino;
    }

    @Override
    public Double getCost() {
        return 0d;
    }

    @Override
    public String toString() {
        return "MoverArriba";
    }
}
