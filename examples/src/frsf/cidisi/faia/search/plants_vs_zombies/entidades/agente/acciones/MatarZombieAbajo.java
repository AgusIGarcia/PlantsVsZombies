package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MatarZombieAbajo extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        Posicion posicionAAtacar = calcularPosicionAAtacar(repolloState.getPosicion());

        repolloState.recolectarSoles();

        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && repolloState.puedoMatarZombie(posicionAAtacar)){
            repolloState.matarZombie(posicionAAtacar);
        }
        
        return repolloState;
    }

    
    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        Posicion posicionAAtacar = calcularPosicionAAtacar(jardinState.getPosicionRepollo());

        jardinState.repolloRecolectaSoles();

        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && jardinState.repolloPuedeMatarZombie(posicionAAtacar)){
            jardinState.matarZombie(posicionAAtacar);
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
    
    private Posicion calcularPosicionAAtacar(Posicion posicionActual) {
        Posicion posicionAAtacar = new Posicion(posicionActual.fila - 1, posicionActual.columna);
        return posicionAAtacar;
    }
}