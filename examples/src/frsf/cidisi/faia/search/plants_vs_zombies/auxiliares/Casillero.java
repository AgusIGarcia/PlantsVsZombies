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

    public Boolean tengoContenido() {
        return girasol != null || zombie != null || cantidadDeSoles > 0;
    }

    public Girasol getGirasol() {
        return girasol;
    }

    public void setGirasol(Girasol girasol) {
        this.girasol = girasol;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

    public Integer getCantidadDeSoles() {
        return cantidadDeSoles;
    }

    public void setCantidadDeSoles(Integer cantidadDeSoles) {
        this.cantidadDeSoles = cantidadDeSoles;
    }
}
