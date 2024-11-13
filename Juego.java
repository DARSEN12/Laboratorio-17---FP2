import java.util.*;
class Juego {
    private Mapa mapa;
    private Movimiento movimiento;
    private Scanner scanner;
    public Juego() {
        mapa = new Mapa();
        movimiento = new Movimiento(mapa);
        scanner = new Scanner(System.in);
    }
    public void iniciar() {
        while (true) {
            mapa.imprimirTablero();
            if (turnoJugador(Reino.INGLATERRA)) break;
            mapa.imprimirTablero();
            if (turnoJugador(Reino.FRANCIA)) break;
        }
        scanner.close();
    }
    private boolean turnoJugador(Reino reino) {
        System.out.println("Turno del jugador: " + reino);
        System.out.print("Ingrese la posición del ejército (x y): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.print("Ingrese la dirección de movimiento (arriba, abajo, izquierda, derecha): ");
        String direccion = scanner.next();
        if (!movimiento.moverEjercito(x, y, direccion, reino)) {
            System.out.println("Movimiento no válido, intenta nuevamente.");
            return false;
        }
        if (verificarVictoria(reino)) {
            System.out.println("¡El reino " + reino + " ha ganado la guerra!");
            return true;
        }
        return false;
    }
    private boolean verificarVictoria(Reino reino) {
        int ejercitosEnemigos = 0;
        for (int i = 0; i < mapa.getFilas(); i++) {
            for (int j = 0; j < mapa.getColumnas(); j++) {
                Ejercito ejercito = mapa.getEjercitoEn(i, j);
                if (ejercito != null && ejercito.getReino() != reino) {
                    ejercitosEnemigos++;
                }
            }
        }
        return ejercitosEnemigos == 0;
    }
}
