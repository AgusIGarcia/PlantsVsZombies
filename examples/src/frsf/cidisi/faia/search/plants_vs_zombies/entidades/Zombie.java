package frsf.cidisi.faia.search.plants_vs_zombies.entidades;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;

public class Zombie {

    private static final Integer CICLOS_MINIMOS_PARA_AVANZAR = 2;
    private static final Integer CICLOS_MAXIMOS_PARA_AVANZAR = 4;

    public TipoZombie tipo;
    public Integer vida;
    private Integer ciclosParaAvanzar;

    public Zombie() {
        this.setearTipoZombie();
        this.reiniciarCiclosParaAvanzar();
    }

    public Zombie(Zombie otroZombie) {
        this.tipo = otroZombie.tipo;
        this.vida = otroZombie.vida;
        this.ciclosParaAvanzar = otroZombie.getCiclosParaAvanzar();
    }

    private void setearTipoZombie() {
        Integer numeroZombieAGenerar = RandomPropio.generarNumeroRandom(1, 6);
        this.tipo = TipoZombie.getTipo(numeroZombieAGenerar);
        this.vida = TipoZombie.getVida(this.tipo);
    }

    public void reiniciarCiclosParaAvanzar(){
        this.ciclosParaAvanzar = RandomPropio.generarNumeroRandom(CICLOS_MINIMOS_PARA_AVANZAR, CICLOS_MAXIMOS_PARA_AVANZAR);
    }

    public void ocurrioCiclo(){
        this.ciclosParaAvanzar--;
    }

    public Boolean puedeAvanzar(){
        return ciclosParaAvanzar <= 0;
    }

    public void avanzar(){
        this.reiniciarCiclosParaAvanzar();
    }

    public Integer danioAlAgente(){
        return this.vida * 2;
    }

    public boolean equals(Zombie otroZombie){
        return this.tipo == otroZombie.tipo;
    }

    public Integer getCiclosParaAvanzar(){
        return this.ciclosParaAvanzar;
    }
}
