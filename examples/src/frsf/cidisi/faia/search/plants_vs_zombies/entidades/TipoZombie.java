package frsf.cidisi.faia.search.plants_vs_zombies.entidades;
public enum TipoZombie {
    ZOMBIE_BASICO,
    ZOMBIE_CONO,
    ZOMBIE_GIGANTE,
    ZOMBIE_BALDE,
    ZOMBIE_BANDERA,
    ZOMBIE_YETI;
    
    public static Integer getVida(TipoZombie tipo){
        switch (tipo) {
            case ZOMBIE_BASICO:
                return 1;
            case ZOMBIE_CONO:
                return 2;
            case ZOMBIE_GIGANTE:
                return 5;
            case ZOMBIE_BALDE:
                return 3;
            case ZOMBIE_BANDERA:
                return 2;
            case ZOMBIE_YETI:
                return 4;
            default:
                return 0;
        }
    }

    public static TipoZombie getTipo(Integer numeroDeZombie){
        switch (numeroDeZombie) {
            case 1:
                return ZOMBIE_BASICO;
            case 2:
                return ZOMBIE_CONO;
            case 3:
                return ZOMBIE_GIGANTE;
            case 4:
                return ZOMBIE_BALDE;
            case 5:
                return ZOMBIE_BANDERA;
            default:
                return ZOMBIE_YETI;
        }
    }
}


