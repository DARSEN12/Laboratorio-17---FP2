import java.util.*;
class Mapa {
    private Territorio territorio;
    private Ejercito[][] tablero;
    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private Random random = new Random();
    public Mapa() {
        generarTerritorio();
        tablero = new Ejercito[FILAS][COLUMNAS];
        generarEjercitosAleatorios();
    }
    private void generarTerritorio() {
        Territorio[] territorios = Territorio.values();
        territorio = territorios[random.nextInt(territorios.length)];
    }
    private void generarEjercitosAleatorios() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (random.nextBoolean()) {
                    Reino reino = Reino.values()[random.nextInt(2)];
                    int soldados = 1 + random.nextInt(10);
                    Ejercito ejercito = new Ejercito(reino, soldados);
                    aplicarBonusTerritorio(ejercito);
                    tablero[i][j] = ejercito;
                }
            }
        }
    }
    private void aplicarBonusTerritorio(Ejercito ejercito) {
        if ((territorio == Territorio.BOSQUE && (ejercito.getReino() == Reino.INGLATERRA || ejercito.getReino() == Reino.SACRO_IMPERIO_ROMANO)) ||
            (territorio == Territorio.CAMPO_ABIERTO && (ejercito.getReino() == Reino.FRANCIA || ejercito.getReino() == Reino.SACRO_IMPERIO_ROMANO)) ||
            (territorio == Territorio.MONTANA && ejercito.getReino() == Reino.CASTILLA_ARAGON) ||
            (territorio == Territorio.DESIERTO && ejercito.getReino() == Reino.MOROS) ||
            (territorio == Territorio.PLAYA && ejercito.getReino() == Reino.SACRO_IMPERIO_ROMANO)) {
            ejercito.incrementarVida(1);
        }
    }
    public Ejercito getEjercitoEn(int x, int y) {
        return tablero[x][y];
    }
    public void moverEjercito(int x, int y, int nuevoX, int nuevoY) {
        tablero[nuevoX][nuevoY] = tablero[x][y];
        tablero[x][y] = null;
    }
    public void eliminarEjercito(int x, int y) {
        tablero[x][y] = null;
    }
    public int getFilas() {
        return FILAS;
    }
    public int getColumnas() {
        return COLUMNAS;
    }
    public Territorio getTerritorio() {
        return territorio;
    }
    public void imprimirTablero() {
        System.out.println("Tablero:");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Ejercito ejercito = tablero[i][j];
                if (ejercito != null) {
                    System.out.print(ejercito + "\t");
                } else {
                    System.out.print("~~\t");
                }
            }
            System.out.println();
        }
    }
}
