package frsf.cidisi.faia.search.plants_vs_zombies.auxiliares;

import frsf.cidisi.faia.search.plants_vs_zombies.entidades.Girasol;
import frsf.cidisi.faia.search.plants_vs_zombies.entidades.Zombie;

public class Casillero {
    public Girasol girasol;
    public Zombie zombie;
    public Integer cantidadDeSoles;

    public Casillero() {
        this.cantidadDeSoles = 0;
    }

    public Casillero(Casillero casilleroACopiar){
        this.girasol = new Girasol(casilleroACopiar.girasol);
        this.zombie = new Zombie(casilleroACopiar.zombie);
        this.cantidadDeSoles = casilleroACopiar.cantidadDeSoles;
    }

    public Boolean tengoContenido() {
        return girasol != null || zombie != null || cantidadDeSoles > 0;
    }

    public boolean equals(Casillero otroCasillero){
        return this.cantidadDeSoles == otroCasillero.cantidadDeSoles && this.equalsGirasoles(otroCasillero.girasol) && this.zombie.equals(otroCasillero.zombie);
    }

    private boolean equalsGirasoles(Girasol otroGirasol) {
        return (this.girasol == null && otroGirasol == null) || (this.girasol != null && otroGirasol != null);
    }

}
