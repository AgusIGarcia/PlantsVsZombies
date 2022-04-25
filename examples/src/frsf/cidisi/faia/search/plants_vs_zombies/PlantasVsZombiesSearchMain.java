package frsf.cidisi.faia.search.plants_vs_zombies;

import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironment;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgent;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;

public class PlantasVsZombiesSearchMain {
    public static void main(String[] args) {
        InicioJuego parametrosInicio = new InicioJuego();

        RepolloBoxeadorAgent repolloAgent = new RepolloBoxeadorAgent(parametrosInicio);

        JardinEnvironment jardinEnvironment = new JardinEnvironment(parametrosInicio);

        SearchBasedAgentSimulator simulator = new SearchBasedAgentSimulator(jardinEnvironment, repolloAgent);

        simulator.start();
    }
}
