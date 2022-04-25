package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import frsf.cidisi.faia.agent.search.GoalTest;
import frsf.cidisi.faia.state.AgentState;

public class RepolloBoxeadorGoal extends GoalTest {

    @Override
    public boolean isGoalState(AgentState agentState) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) agentState;
        return repolloState.victoria();
    }

}
