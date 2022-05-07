package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MatarZombieArriba extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        Posicion posicionAAtacar = calcularPosicionAAtacar(repolloState.getPosicion());

        repolloState.recolectarSoles();

        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && repolloState.puedoMatarZombie(posicionAAtacar)){
            repolloState.matarZombie(posicionAAtacar);
            repolloState.sumarTurno();
            return repolloState;
        }
        
        return null;
    }

    
    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) ast;
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        Posicion posicionAAtacar = calcularPosicionAAtacar(jardinState.getPosicionRepollo());

        jardinState.repolloRecolectaSoles();
        repolloState.recolectarSoles();

        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && jardinState.repolloPuedeMatarZombie(posicionAAtacar)){
            jardinState.matarZombie(posicionAAtacar);
            repolloState.matarZombie(posicionAAtacar);
            repolloState.sumarTurno();
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
        return "MatarZombieArriba";
    }
    
    private Posicion calcularPosicionAAtacar(Posicion posicionActual) {
        Posicion posicionAAtacar = new Posicion(posicionActual.fila - 1, posicionActual.columna);
        return posicionAAtacar;
    }
}
