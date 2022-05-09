package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.RepolloBoxeadorAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MatarZombieDerecha extends SearchAction {

    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) s;

        Posicion posicionAAtacar = calcularPosicionAAtacar(repolloState.getPosicion());


        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && repolloState.puedoMatarZombie(posicionAAtacar)){
            repolloState.recolectarSoles();
            repolloState.matarZombie(posicionAAtacar);
            repolloState.sumarTurno();
            repolloState.agregarCosto(10);
            return repolloState;
        }
        
        return null;
    }

    
    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        RepolloBoxeadorAgentState repolloState = (RepolloBoxeadorAgentState) ast;
        JardinEnvironmentState jardinState = (JardinEnvironmentState) est;

        Posicion posicionAAtacar = calcularPosicionAAtacar(jardinState.getPosicionRepollo());

        if(JardinEnvironmentState.posicionValida(posicionAAtacar) && jardinState.repolloPuedeMatarZombie(posicionAAtacar)){
            jardinState.repolloRecolectaSoles();
            repolloState.recolectarSoles();
            jardinState.matarZombie(posicionAAtacar);
            repolloState.matarZombie(posicionAAtacar);
            repolloState.sumarTurno();
            repolloState.agregarCosto(10);
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
        return "MatarZombieDerecha";
    }
    
    private Posicion calcularPosicionAAtacar(Posicion posicionActual) {
        Posicion posicionAAtacar = new Posicion(posicionActual.fila, posicionActual.columna + 1);
        return posicionAAtacar;
    }
}