package src;

public class MatrizEstacionHora {
    private String[] estaciones;
    private double[][] matrizPm25;

    public MatrizEstacionHora(String[] estaciones) {
        this.estaciones = estaciones;
        this.matrizPm25 = new double[estaciones.length][24];
        
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
        System.out.println("\n=== MATRIZ ESTACIÓN x HORA (PM2.5) ===");
        
        // Imprimir en 4 bloques de 6 horas para legibilidad en consola
        for (int bloque = 0; bloque < 24; bloque += 6) {
            int horaFin = bloque + 6;
            System.out.printf("\n--- Horario %02d:00 a %02d:00 ---\n", bloque, horaFin - 1);
            
            System.out.printf("%-10s", "Estación");
            for (int h = bloque; h < horaFin; h++) {
                System.out.printf("%-8s", String.format("%02d:00", h));
            }
            System.out.println();

            for (int i = 0; i < estaciones.length; i++) {
                System.out.printf("%-10s", estaciones[i]);
                for (int j = bloque; j < horaFin; j++) {
                    if (matrizPm25[i][j] == -1) {
                        System.out.printf("%-8s", "N/D");
                    } else {
                        System.out.printf("%-8.1f", matrizPm25[i][j]);
                    }
                }
                System.out.println();
            }
        }
    }
}