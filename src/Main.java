package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE MONITOREO AMBIENTAL ===");
        
        // Ruta del archivo CSV de prueba (asegúrate de crearlo en la carpeta datos/)
        String rutaArchivo = "datos/lecturas_prueba.csv";

        ProcesadorIngesta procesador = new ProcesadorIngesta();
        procesador.procesarArchivo(rutaArchivo);

        // Muestra el reporte final de la ingesta confiable
        procesador.mostrarReporte();
    }
}