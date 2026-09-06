package src;

public class RepositorioLecturas {
    private LecturaSensor[] lecturas;
    private int tamanio;
    private static final int CAPACIDAD_INICIAL = 10;

    public RepositorioLecturas() {
        this.lecturas = new LecturaSensor[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    public void agregar(LecturaSensor lectura) {
        if (tamanio == lecturas.length) {
            redimensionar();
        }
        lecturas[tamanio] = lectura;
        tamanio++;
    }

    private void redimensionar() {
        LecturaSensor[] nuevoArreglo = new LecturaSensor[lecturas.length * 2];
        for (int i = 0; i < lecturas.length; i++) {
            nuevoArreglo[i] = lecturas[i];
        }
        this.lecturas = nuevoArreglo;
    }

    public LecturaSensor obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return lecturas[indice];
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getCapacidad() {
        return lecturas.length;
    }
}