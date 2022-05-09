package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import frsf.cidisi.faia.solver.search.IEstimatedCostFunction;
import frsf.cidisi.faia.solver.search.NTree;

public class AgentHeuristic implements IEstimatedCostFunction {

    @Override
    public double getEstimatedCost(NTree node) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) node.getAgentState();

        return (repolloState.getZombiesPorMatar() + repolloState.todasLasFilasVisitadasHeuristica()//repolloState.meMoviHeuristica()
                + repolloState.girasolesRestantesPrimerColumna());
    }
}
