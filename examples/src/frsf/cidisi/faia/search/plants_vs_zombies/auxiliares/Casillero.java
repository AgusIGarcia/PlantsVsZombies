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

    public Casillero(Casillero casilleroACopiar) {
        this.girasol = casilleroACopiar.girasol == null ? null : new Girasol(casilleroACopiar.girasol);
        this.zombie = casilleroACopiar.zombie == null ? null : new Zombie(casilleroACopiar.zombie);
        this.cantidadDeSoles = casilleroACopiar.cantidadDeSoles;
    }

    public Boolean tengoContenido() {
        return girasol != null || zombie != null || cantidadDeSoles > 0;
    }

    public boolean equals(Casillero otroCasillero) {
        return this.cantidadDeSoles == otroCasillero.cantidadDeSoles && this.equalsGirasoles(otroCasillero.girasol)
                && this.equalsZombies(otroCasillero.zombie);
    }

    private boolean equalsGirasoles(Girasol otroGirasol) {
        return (this.girasol == null && otroGirasol == null) || (this.girasol != null && otroGirasol != null);
    }

    private boolean equalsZombies(Zombie otroZombie) {
        return ((this.zombie == null && otroZombie == null) || ((this.zombie != null && otroZombie != null) && this.zombie.equals(otroZombie)));
    }
    
    @Override
    public String toString(){
        String result = "";
        if(this.girasol != null){
            result += "G, ";
        }
        else {
            result += "_, ";
        }
        if(this.zombie != null){
            result += "Z, ";
        }
        else {
            result += "_, ";
        }
        result += cantidadDeSoles;
        return result;
    }
}
