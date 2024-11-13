import java.util.Random;

class Movimiento {
    private Mapa mapa;
    private Random random = new Random();

    public Movimiento(Mapa mapa) {
        this.mapa = mapa;
    }

    public boolean moverEjercito(int x, int y, String direccion, Reino reino) {
        // Verificar si hay un ejército en la posición inicial y que pertenezca al reino del jugador
        Ejercito ejercito = mapa.getEjercitoEn(x, y);
        if (ejercito == null || ejercito.getReino() != reino) {
            System.out.println("No hay un ejército de tu reino en esta posición. Intenta nuevamente.");
            return false;
        }

        // Calcular las nuevas coordenadas según la dirección
        int[] nuevaPos = calcularNuevaPosicion(x, y, direccion);
        if (nuevaPos == null || !esMovimientoValido(nuevaPos[0], nuevaPos[1], reino)) {
            System.out.println("Movimiento inválido. Intenta nuevamente.");
            return false;
        }

        // Realizar el movimiento y resolver batalla si es necesario
        return realizarMovimiento(x, y, nuevaPos[0], nuevaPos[1], ejercito);
    }

    private int[] calcularNuevaPosicion(int x, int y, String direccion) {
        switch (direccion.toLowerCase()) {
            case "arriba": return new int[]{x - 1, y};
            case "abajo": return new int[]{x + 1, y};
            case "izquierda": return new int[]{x, y - 1};
            case "derecha": return new int[]{x, y + 1};
            default: return null;
        }
    }

    private boolean esMovimientoValido(int nuevoX, int nuevoY, Reino reino) {
        // Verificar si la posición está dentro de los límites del tablero
        if (nuevoX < 0 || nuevoX >= mapa.getFilas() || nuevoY < 0 || nuevoY >= mapa.getColumnas()) {
            return false;
        }
        // Verificar que no haya un ejército del mismo reino en la posición destino
        Ejercito ejercitoDestino = mapa.getEjercitoEn(nuevoX, nuevoY);
        return ejercitoDestino == null || ejercitoDestino.getReino() != reino;
    }

    private boolean realizarMovimiento(int x, int y, int nuevoX, int nuevoY, Ejercito ejercito) {
        Ejercito ejercitoEnDestino = mapa.getEjercitoEn(nuevoX, nuevoY);
        if (ejercitoEnDestino == null) {
            // Movimiento simple a una posición vacía
            mapa.moverEjercito(x, y, nuevoX, nuevoY);
            System.out.println("Ejército movido a una posición vacía.");
        } else {
            // Resolver batalla entre ejércitos rivales
            resolverBatalla(ejercito, ejercitoEnDestino, x, y, nuevoX, nuevoY);
        }
        return true;
    }

    private void resolverBatalla(Ejercito ejercito1, Ejercito ejercito2, int x, int y, int nuevoX, int nuevoY) {
        int vidaTotal = ejercito1.getSoldados() + ejercito2.getSoldados();
        double probabilidadEjercito1 = (double) ejercito1.getSoldados() / vidaTotal;
        double probabilidadEjercito2 = (double) ejercito2.getSoldados() / vidaTotal;

        System.out.printf("Batalla entre %s (%d soldados) y %s (%d soldados)\n",
                ejercito1.getReino(), ejercito1.getSoldados(), ejercito2.getReino(), ejercito2.getSoldados());
        System.out.printf("Probabilidades: %s %.2f%%, %s %.2f%%\n",
                ejercito1.getReino(), probabilidadEjercito1 * 100,
                ejercito2.getReino(), probabilidadEjercito2 * 100);

        double resultado = random.nextDouble();
        if (resultado < probabilidadEjercito1) {
            // Ejército 1 gana
            mapa.moverEjercito(x, y, nuevoX, nuevoY);
            ejercito1.incrementarVida(1); // Bonificación de vida para el ganador
            System.out.println("Gana el ejército de " + ejercito1.getReino() + ". Incremento de vida en 1.");
        } else {
            // Ejército 2 gana
            mapa.eliminarEjercito(x, y);
            ejercito2.incrementarVida(1); // Bonificación de vida para el ganador
            System.out.println("Gana el ejército de " + ejercito2.getReino() + ". Incremento de vida en 1.");
        }
    }
}
