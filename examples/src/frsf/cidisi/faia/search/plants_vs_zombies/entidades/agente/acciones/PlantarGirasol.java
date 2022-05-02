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
            return repolloState;
        }
        
        return null;
    }

    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) ast;

        jardinState.repolloRecolectaSoles();
        repolloState.recolectarSoles();

        if(jardinState.sePuedePlantarGirasol()){
            repolloState.plantarGirasol();
            jardinState.plantarGirasol();
            return jardinState;
        }

        return null;
    }

    @Override
    public Double getCost() {
        return 0d;
    }

    @Override
    public String toString() {
        return "PlantarGirasol";
    }

}
