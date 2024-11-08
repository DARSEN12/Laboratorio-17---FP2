import java.util.Random;

public class Ejercito {
    private String reino;
    private int numSoldados;
    private int nivelVida;
    private int fila;
    private int columna;

    public Ejercito(String reino, int numSoldados) {
        this.reino = reino;
        this.numSoldados = numSoldados;
        this.nivelVida = calcularNivelVida();
    }

    // Método para calcular la vida inicial de los soldados
    private int calcularNivelVida() {
        Random random = new Random();
        int vidaBase = 10;  // Vida base de cada soldado
        return vidaBase * numSoldados + random.nextInt(5); // Vida base más un rango adicional aleatorio
    }

    public String getReino() {
        return reino;
    }

    public int getNivelVidaTotal() {
        return nivelVida;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setPosition(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    // Aumenta la vida en el bono indicado
    public void aumentarNivelVida(int bono) {
        this.nivelVida += bono;
    }

    // Representación de la vida de cada soldado del ejército
    public String toString() {
        return "Ejercito{" +
                "reino='" + reino + '\'' +
                ", numSoldados=" + numSoldados +
                ", nivelVida=" + nivelVida +
                '}';
    }
}

