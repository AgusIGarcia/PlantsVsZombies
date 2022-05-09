package frsf.cidisi.faia.search.plants_vs_zombies;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.GoalBasedAgent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironment;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.EscribirJSON;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;
import frsf.cidisi.faia.simulator.events.EventType;
import frsf.cidisi.faia.simulator.events.SimulatorEventNotifier;

public class PvZSearchBasedAgentSimulator extends SearchBasedAgentSimulator {

    public PvZSearchBasedAgentSimulator(Environment environment, Agent agent) {
        super(environment, agent);
    }

    @Override
    public void start() {

        System.out.println("----------------------------------------------------");
        System.out.println("--- " + this.getSimulatorName() + " ---");
        System.out.println("----------------------------------------------------");
        System.out.println();

        Perception perception;
        Action action;
        GoalBasedAgent agent;
        EscribirJSON escritor = new EscribirJSON();

        agent = (GoalBasedAgent) this.getAgents().firstElement();

        /*
         * Simulation starts. The environment sends perceptions to the agent, and
         * it returns actions. The loop condition evaluation is placed at the end.
         * This works even when the agent starts with a goal state (see agentSucceeded
         * method in the SearchBasedAgentSimulator).
         */
        do {

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------");

            System.out.println("Sending perception to agent...");
            perception = this.getPercept();
            agent.see(perception);
            System.out.println("Perception: " + perception);

            System.out.println("Agent State: " + agent.getAgentState());
            System.out.println("Environment: " + environment.toString());

            escritor.escribirEstado((JardinEnvironmentState) environment.getEnvironmentState());

            System.out.println("Asking the agent for an action...");
            action = agent.selectAction();

            if (action == null) {
                escritor.escribirAccion(action);
                escritor.escribirTermino(false);
                break;
            }

            escritor.escribirAccion(action);

            System.out.println("Action returned: " + action);
            System.out.println();

            this.actionReturned(agent, action);
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------");

            escritor.escribirTermino();

        } while (!this.agentSucceeded(action) && !this.agentFailed(action));
        
        escritor.escribirEstado((JardinEnvironmentState) environment.getEnvironmentState());
        escritor.escribirAccion(action);

        // Check what happened, if agent has reached the goal or not.
        if (this.agentSucceeded(action)) {
            escritor.escribirTermino(true);
            System.out.println("Agent has reached the goal!");
        } else {
            escritor.escribirTermino(false);
            System.out.println("ERROR: The simulation has finished, but the agent has not reached his goal.");
        }

        // Leave a blank line
        System.out.println();

        escritor.finalizar();

        // FIXME: This call can be moved to the Simulator class
        this.environment.close();

        // Launch simulationFinished event
        SimulatorEventNotifier.runEventHandlers(EventType.SimulationFinished, null);
    }

    

    @Override
    public boolean agentSucceeded(Action actionReturned) {

        JardinEnvironment jardin = (JardinEnvironment) this.environment;
        return jardin.esWinAgente();
    }

}
