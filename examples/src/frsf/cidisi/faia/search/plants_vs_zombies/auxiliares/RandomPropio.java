package frsf.cidisi.faia.search.plants_vs_zombies.auxiliares;

import java.util.Random;

public class RandomPropio {

    public static Integer generarNumeroRandom(Integer min, Integer max) {

        //CORREGIR!!!!!!!!!!
        Random random = new Random();
        return random.nextInt(max + 1 + min) + min;
    }
}
