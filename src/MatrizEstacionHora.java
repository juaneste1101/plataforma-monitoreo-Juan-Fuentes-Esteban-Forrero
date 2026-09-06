package src;

public class MatrizEstacionHora {
    private String[] estaciones;
    private double[][] matrizPm25; // Filas: Estaciones, Columnas: Horas (0-23)

    public MatrizEstacionHora(String[] estaciones) {
        this.estaciones = estaciones;
        // Se inicializa la matriz con el número de estaciones y 24 horas
        this.matrizPm25 = new double[estaciones.length][24];
        
        // Inicializar posiciones en -1 para diferenciar horas sin datos de mediciones en 0
        for (int i = 0; i < estaciones.length; i++) {
            for (int j = 0; j < 24; j++) {
                matrizPm25[i][j] = -1;
            }
        }
    }

    private int buscarIndiceEstacion(String idEstacion) {
        for (int i = 0; i < estaciones.length; i++) {
            if (estaciones[i].equalsIgnoreCase(idEstacion)) {
                return i;
            }
        }
        return -1;
    }

    public void registrarMedicion(String idEstacion, int hora, double pm25) {
        int indiceEstacion = buscarIndiceEstacion(idEstacion);
        if (indiceEstacion != -1 && hora >= 0 && hora < 24) {
            matrizPm25[indiceEstacion][hora] = pm25;
        }
    }

    public void mostrarMatriz() {
        System.out.println("\n=== MATRIZ DE MATRICES: ESTACIÓN x HORA (PM2.5) ===");
        System.out.print("Estación / Hora\t");
        for (int h = 0; h < 24; h += 3) { // Muestra encabezados cada 3 horas para legibilidad
            System.out.printf("%02d:00\t", h);
        }
        System.out.println();

        for (int i = 0; i < estaciones.length; i++) {
            System.out.print(estaciones[i] + "\t\t");
            for (int j = 0; j < 24; j += 3) {
                if (matrizPm25[i][j] == -1) {
                    System.out.print("N/D\t");
                } else {
                    System.out.printf("%.1f\t", matrizPm25[i][j]);
                }
            }
            System.out.println();
        }
    }
}