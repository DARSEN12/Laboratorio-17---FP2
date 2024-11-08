import java.util.*;
public class Juego {
    public static void main(String[] args) {
        Mapa mapa = new Mapa(5, 5);  // Tamaño del tablero
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            mapa.mostrarMapa();

            for (String reino : mapa.getReinos()) {
                System.out.println("Turno para el reino " + reino);
                System.out.print("Ingrese fila, columna y dirección (ARRIBA, ABAJO, IZQUIERDA, DERECHA): ");
                int fila = scanner.nextInt();
                int columna = scanner.nextInt();
                String direccion = scanner.next();

                mapa.moverEjercito(reino, fila, columna, direccion);

                if (mapa.reinoSinEjercitos()) {
                    System.out.println("¡El reino " + reino + " ha ganado la guerra!");
                    return;
                }
            }
        }
    }
}
