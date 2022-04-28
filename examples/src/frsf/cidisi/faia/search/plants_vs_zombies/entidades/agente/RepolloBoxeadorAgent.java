package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.Problem;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgent;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.InicioJuego;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MatarZombieAbajo;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MatarZombieArriba;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MatarZombieDerecha;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MatarZombieIzquierda;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MoverAbajo;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MoverArriba;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MoverDerecha;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.MoverIzquierda;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones.PlantarGirasol;
import frsf.cidisi.faia.solver.search.DepthFirstSearch;
import frsf.cidisi.faia.solver.search.Search;

public class RepolloBoxeadorAgent extends SearchBasedAgent {

    public RepolloBoxeadorAgent(InicioJuego parametrosInicio) {
        super();
        RepolloBoxeadorGoal goal = new RepolloBoxeadorGoal();

        RepolloBoxeadorAgentState repolloState = new RepolloBoxeadorAgentState(parametrosInicio);
        this.setAgentState(repolloState);

        Vector<SearchAction> operators = new Vector<SearchAction>();
        operators.add(new PlantarGirasol());
        operators.add(new MoverArriba());
        operators.add(new MoverAbajo());
        operators.add(new MoverIzquierda());
        operators.add(new MoverDerecha());
        operators.add(new MatarZombieArriba());
        operators.add(new MatarZombieAbajo());
        operators.add(new MatarZombieIzquierda());
        operators.add(new MatarZombieDerecha());

        Problem problem = new Problem(goal, repolloState, operators);
        this.setProblem(problem);
    }

    @Override
    public void see(Perception p) {
        this.getAgentState().updateState(p);
    }

    @Override
    public Action selectAction() {
        // Create the search strategy
        DepthFirstSearch strategy = new DepthFirstSearch();

        /**
         * Another search strategy examples:
         * 
         * Depth First Search:
         * DepthFirstSearch strategy = new DepthFirstSearch();
         * 
         * Breath First Search:
         * BreathFirstSearch strategy = new BreathFirstSearch();
         * 
         * Uniform Cost:
         * IStepCostFunction costFunction = new CostFunction();
         * UniformCostSearch strategy = new UniformCostSearch(costFunction);
         * 
         * A Star Search:
         * IStepCostFunction cost = new CostFunction();
         * IEstimatedCostFunction heuristic = new Heuristic();
         * AStarSearch strategy = new AStarSearch(cost, heuristic);
         * 
         * Greedy Search:
         * IEstimatedCostFunction heuristic = new Heuristic();
         * GreedySearch strategy = new GreedySearch(heuristic);
         */

        // Create a Search object with the strategy
        Search searchSolver = new Search(strategy);

        /*
         * Generate an XML file with the search tree. It can also be generated
         * in other formats like PDF with PDF_TREE
         */
        searchSolver.setVisibleTree(Search.EFAIA_TREE);

        // Set the Search searchSolver.
        this.setSolver(searchSolver);

        // Ask the solver for the best action
        Action selectedAction = null;
        try {
            selectedAction = this.getSolver().solve(new Object[] { this.getProblem() });
        } catch (Exception ex) {
            Logger.getLogger(RepolloBoxeadorAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Return the selected action
        return selectedAction;
    }

}
