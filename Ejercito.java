class Ejercito {
    private Reino reino;
    private int soldados;
    public Ejercito(Reino reino, int soldados) {
        this.reino = reino;
        this.soldados = soldados;
    }
    public Reino getReino() {
        return reino;
    }
    public int getSoldados() {
        return soldados;
    }
    public void incrementarVida(int cantidad) {
        this.soldados += cantidad;
    }
    @Override
    public String toString() {
        return reino.toString().charAt(0) + String.valueOf(soldados);
    }
}
