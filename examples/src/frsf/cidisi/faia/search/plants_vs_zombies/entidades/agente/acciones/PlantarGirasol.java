package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class PlantarGirasol extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        repolloState.recolectarSoles();

        if(repolloState.sePuedePlantarGirasol()){
            repolloState.plantarGirasol();
        }
        
        return repolloState;
    }

    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        jardinState.repolloRecolectaSoles();

        if(jardinState.sePuedePlantarGirasol()){
            jardinState.plantarGirasol();
        }

        return jardinState;
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
