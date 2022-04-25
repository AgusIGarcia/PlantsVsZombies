package frsf.cidisi.faia.search.plants_vs_zombies.entidades.agente.acciones;

import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;
import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.Posicion;

public class MoverHelperAmbiente {
    
    private Posicion posicionDestino;
    private JardinEnvironmentState jardinState;

    public MoverHelperAmbiente(Posicion posicionDestino, JardinEnvironmentState jardinState) {
        this.posicionDestino = posicionDestino;
        this.jardinState = jardinState;
    }

    public JardinEnvironmentState execute(){
        
        if (JardinEnvironmentState.posicionValida(posicionDestino)) {
            preMovimiento();
            actualizarPosicionAgente();
            postMovimiento();
        }

        return this.jardinState;
    }

    public void preMovimiento(){
        this.jardinState.repolloRecolectaSoles();
    }

    public void actualizarPosicionAgente() {
        this.jardinState.setPosicionRepollo(posicionDestino);
    }

    private void postMovimiento() {
        this.jardinState.repolloRecolectaSoles();
        this.jardinState.repolloPierdeVidaPostAvanzar();
    }
}
