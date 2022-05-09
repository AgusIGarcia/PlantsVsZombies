package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import frsf.cidisi.faia.solver.search.IStepCostFunction;
import frsf.cidisi.faia.solver.search.NTree;

public class AgentCostFunction implements IStepCostFunction{

    @Override
    public double calculateCost(NTree node) {
        return ((RepolloBoxeadorAgentState) node.getAgentState()).getCosto();
    }
    
}
