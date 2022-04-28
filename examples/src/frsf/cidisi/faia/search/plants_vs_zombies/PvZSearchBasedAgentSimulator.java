package frsf.cidisi.faia.search.plants_vs_zombies;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironment;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;

public class PvZSearchBasedAgentSimulator extends SearchBasedAgentSimulator{

    public PvZSearchBasedAgentSimulator(Environment environment, Agent agent) {
        super(environment, agent);
    }

    @Override
    public boolean agentSucceeded(Action actionReturned) {

        JardinEnvironment jardin = (JardinEnvironment) this.environment;
        return jardin.esWinAgente();
    }
    
}
