package frsf.cidisi.faia.search.plants_vs_zombies.auxiliares;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.search.plants_vs_zombies.ambiente.JardinEnvironmentState;

public class EscribirJSON {

    private File archivoSalida;
    private FileWriter escritor;
    private JardinEnvironmentState environmentState;

    public EscribirJSON() {
        super();
        this.archivoSalida = new File(
                "C:\\Users\\garag\\Documents\\Facultad\\IA\\plants-vs-zombies\\src\\execute\\resultado.json");
        try {
            this.escritor = new FileWriter(this.archivoSalida);
            this.escritor.write("{\n");
            this.escritor.write("\"ejecucion\": [\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribirEstado(JardinEnvironmentState environmentState) {
        this.environmentState = environmentState;
        try {
            this.escritor.write("{\n");
            this.escribirTurno();
            this.escribirEnergiaAgente();
            this.escribirZombiesPorGenerar();
            this.escribirJardin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escribirTurno() throws IOException {
        this.escritor.write("\"turno\":" + this.environmentState.getTurno() + ",\n");
    }

    private void escribirEnergiaAgente() throws IOException {
        this.escritor.write("\"energiaAgente\":" + this.environmentState.getEnergiaRepollo() + ",\n");
    }

    private void escribirZombiesPorGenerar() throws IOException {
        this.escritor.write("\"zombiesPorGenerar\":" + this.environmentState.getCantidadZombiesPorGenerar() + ",\n");
    }

    private void escribirJardin() throws IOException {
        this.escritor.write("\"jardin\":[\n");
        Casillero[][] jardin = this.environmentState.getJardin();
        for (int fila = JardinEnvironmentState.PRIMERA_FILA; fila <= JardinEnvironmentState.ULTIMA_FILA; fila++) {
            for (int columna = JardinEnvironmentState.PRIMERA_COLUMNA; columna <= JardinEnvironmentState.ULTIMA_COLUMNA; columna++) {
                this.escritor.write("{\n");
                Boolean esLaPosicionDelRepollo = this.environmentState.getPosicionRepollo()
                        .equals(new Posicion(fila, columna));
                if (esLaPosicionDelRepollo) {
                    this.escritor.write("\"planta\":true,\n");
                } else {
                    this.escritor.write("\"planta\":false,\n");
                }
                if (jardin[fila][columna].girasol != null) {
                    this.escritor.write("\"girasol\":true,\n");
                } else {
                    this.escritor.write("\"girasol\":false,\n");
                }
                if (jardin[fila][columna].zombie != null) {
                    this.escritor.write("\"zombie\":true,\n");
                    this.escritor.write("\"tipoZombie\":\"" + jardin[fila][columna].zombie.tipo + "\",\n");
                } else {
                    this.escritor.write("\"zombie\":false,\n");
                    this.escritor.write("\"tipoZombie\":\"NINGUNO\",\n");
                }
                this.escritor.write("\"cantidadDeSoles\":" + jardin[fila][columna].cantidadDeSoles);
                if (fila == JardinEnvironmentState.ULTIMA_FILA && columna == JardinEnvironmentState.ULTIMA_COLUMNA) {
                    this.escritor.write("\n}\n");
                } else {
                    this.escritor.write("\n},\n");
                }
            }
        }
        this.escritor.write("],\n");
    }

    public void escribirAccion(Action action) {
        try {
            this.escritor.write("\"accion\":\"" + action + "\",\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirTermino(Boolean gano) {
        try {
            if(gano){
                this.escritor.write("\"gano\":true\n");
            }else{
                this.escritor.write("\"gano\":false\n");
            }
            this.escritor.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirTermino() {
        try {
            this.escritor.write("\"gano\":null\n");
            this.escritor.write("},\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalizar() {
        try {
            this.escritor.write("]\n}");
            this.escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
