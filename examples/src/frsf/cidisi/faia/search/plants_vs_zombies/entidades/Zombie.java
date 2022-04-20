package frsf.cidisi.faia.search.plants_vs_zombies.entidades;

import frsf.cidisi.faia.search.plants_vs_zombies.auxiliares.RandomPropio;

public class Zombie {

    private static final Integer CICLOS_MINIMOS_PARA_AVANZAR = 1;
    private static final Integer CICLOS_MAXIMOS_PARA_AVANZAR = 3;

    public TipoZombie tipo;
    public Integer vida;
    private Integer ciclosParaAvanzar;

    public Zombie() {
        this.setearTipoZombie();
        this.reiniciarCiclosParaAvanzar();
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
        return ciclosParaAvanzar == 0;
    }

    public void avanzar(){
        this.reiniciarCiclosParaAvanzar();
    }
}
