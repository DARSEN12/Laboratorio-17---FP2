import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Mapa {
    private String[][] tablero;
    private String tipoTerritorio;
    private HashMap<String, ArrayList<Ejercito>> ejercitosPorReino;
    private static final String[] DIRECCIONES = {"ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA"};

    public Mapa(int filas, int columnas) {
        this.tablero = new String[filas][columnas];
        this.tipoTerritorio = generarTerritorio();
        this.ejercitosPorReino = new HashMap<>();
        generarEjercitos();
        posicionarEjercitos();
    }

    // Genera el tipo de territorio aleatoriamente
    private String generarTerritorio() {
        String[] territorios = {"bosque", "campo abierto", "montaña", "desierto", "playa"};
        return territorios[new Random().nextInt(territorios.length)];
    }

    // Genera ejércitos de dos reinos
    private void generarEjercitos() {
        String[] reinos = {"Inglaterra", "Francia", "Castilla-Aragón", "Moros", "Sacro Imperio"};
        Random random = new Random();

        // Selección aleatoria de 2 reinos
        String reino1 = reinos[random.nextInt(reinos.length)];
        String reino2;
        do {
            reino2 = reinos[random.nextInt(reinos.length)];
        } while (reino2.equals(reino1));

        // Genera entre 1 y 10 ejércitos con entre 1 y 10 soldados
        ejercitosPorReino.put(reino1, generarEjercitosParaReino(reino1));
        ejercitosPorReino.put(reino2, generarEjercitosParaReino(reino2));
    }

    private ArrayList<Ejercito> generarEjercitosParaReino(String reino) {
        Random random = new Random();
        ArrayList<Ejercito> ejercitos = new ArrayList<>();
        int numEjercitos = random.nextInt(10) + 1;
        for (int i = 0; i < numEjercitos; i++) {
            int numSoldados = random.nextInt(10) + 1;
            ejercitos.add(new Ejercito(reino, numSoldados));
        }
        return ejercitos;
    }

    // Posiciona los ejércitos aleatoriamente en el tablero
    private void posicionarEjercitos() {
        Random random = new Random();
        for (ArrayList<Ejercito> ejercitos : ejercitosPorReino.values()) {
            for (Ejercito ejercito : ejercitos) {
                int fila, columna;
                do {
                    fila = random.nextInt(tablero.length);
                    columna = random.nextInt(tablero[0].length);
                } while (tablero[fila][columna] != null);
                tablero[fila][columna] = ejercito.getReino();
                ejercito.setPosition(fila, columna);
            }
        }
    }

    public void mostrarMapa() {
        for (String[] fila : tablero) {
            for (String celda : fila) {
                System.out.print((celda != null ? celda : ".") + " ");
            }
            System.out.println();
        }
    }

    public void moverEjercito(String reino, int fila, int columna, String direccion) {
        Ejercito ejercito = getEjercitoEnPosicion(reino, fila, columna);
        if (ejercito == null) {
            System.out.println("No hay ejército del reino en esa posición.");
            return;
        }

        int nuevaFila = fila;
        int nuevaColumna = columna;

        switch (direccion.toUpperCase()) {
            case "ARRIBA":
                nuevaFila--;
                break;
            case "ABAJO":
                nuevaFila++;
                break;
            case "IZQUIERDA":
                nuevaColumna--;
                break;
            case "DERECHA":
                nuevaColumna++;
                break;
            default:
                System.out.println("Dirección inválida.");
                return;
        }

        if (!esMovimientoValido(nuevaFila, nuevaColumna)) {
            System.out.println("Movimiento no válido.");
            return;
        }

        if (tablero[nuevaFila][nuevaColumna] != null) {
            String enemigoReino = tablero[nuevaFila][nuevaColumna];
            if (!enemigoReino.equals(reino)) {
                resolverBatalla(ejercito, getEjercitoEnPosicion(enemigoReino, nuevaFila, nuevaColumna));
            }
        } else {
            tablero[fila][columna] = null;
            tablero[nuevaFila][nuevaColumna] = reino;
            ejercito.setPosition(nuevaFila, nuevaColumna);
        }
    }

    private boolean esMovimientoValido(int fila, int columna) {
        return fila >= 0 && columna >= 0 && fila < tablero.length && columna < tablero[0].length;
    }

    private Ejercito getEjercitoEnPosicion(String reino, int fila, int columna) {
        return ejercitosPorReino.get(reino).stream()
                .filter(e -> e.getFila() == fila && e.getColumna() == columna)
                .findFirst().orElse(null);
    }

    private void resolverBatalla(Ejercito ejercito1, Ejercito ejercito2) {
        double totalVida = ejercito1.getNivelVidaTotal() + ejercito2.getNivelVidaTotal();
        double probabilidadEjercito1 = ejercito1.getNivelVidaTotal() / totalVida;

        if (Math.random() < probabilidadEjercito1) {
            System.out.println("Gana " + ejercito1.getReino());
            ejercito1.aumentarNivelVida(1);
            eliminarEjercito(ejercito2);
        } else {
            System.out.println("Gana " + ejercito2.getReino());
            ejercito2.aumentarNivelVida(1);
            eliminarEjercito(ejercito1);
        }
    }

    private void eliminarEjercito(Ejercito ejercito) {
        tablero[ejercito.getFila()][ejercito.getColumna()] = null;
        ejercitosPorReino.get(ejercito.getReino()).remove(ejercito);
    }
}
