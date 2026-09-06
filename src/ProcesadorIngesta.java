package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorIngesta {
    private List<LecturaSensor> lecturasValidas;
    private List<LecturaSensor> lecturasCuarentena;

    public ProcesadorIngesta() {
        this.lecturasValidas = new ArrayList<>();
        this.lecturasCuarentena = new ArrayList<>();
    }

    public void procesarArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esEncabezado = true;

            while ((linea = br.readLine()) != null) {
                if (esEncabezado) {
                    esEncabezado = false;
                    continue; // Salta la cabecera del CSV
                }

                try {
                    String[] partes = linea.split(",");
                    if (partes.length < 5) {
                        System.out.println("[DESCARTADA] Fila incompleta: " + linea);
                        continue;
                    }

                    String idSensor = partes[0].trim();
                    String timestamp = partes[1].trim();
                    double temperatura = Double.parseDouble(partes[2].trim());
                    double humedad = Double.parseDouble(partes[3].trim());
                    double pm25 = Double.parseDouble(partes[4].trim());

                    LecturaSensor lectura = new LecturaSensor(idSensor, timestamp, temperatura, humedad, pm25);

                    if (lectura.isValida()) {
                        lecturasValidas.add(lectura);
                    } else {
                        lecturasCuarentena.add(lectura);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("[ERROR DE FORMATO] No se pudieron parsear los números en la línea: " + linea);
                }
            }

        } catch (IOException e) {
            System.out.println("[ERROR CRÍTICO] No se pudo leer el archivo: " + e.getMessage());
        }
    }

    public List<LecturaSensor> getLecturasValidas() {
        return lecturasValidas;
    }

    public List<LecturaSensor> getLecturasCuarentena() {
        return lecturasCuarentena;
    }

    public void mostrarReporte() {
        System.out.println("=== REPORTE DE INGESTA CONFIABLE ===");
        System.out.println("Total válidas: " + lecturasValidas.size());
        System.out.println("Total en cuarentena (basura/desconectados): " + lecturasCuarentena.size());
        System.out.println("\n--- Detalle de Cuarentena ---");
        for (LecturaSensor l : lecturasCuarentena) {
            System.out.println(l);
        }
    }
}