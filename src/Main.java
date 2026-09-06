package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE MONITOREO AMBIENTAL (SEMANA 02) ===");
        
        String rutaArchivo = "datos/lecturas_prueba.csv";

        ProcesadorIngesta procesador = new ProcesadorIngesta();
        procesador.procesarArchivo(rutaArchivo);

        // 1. Cargar lecturas válidas en el Repositorio Dinámico (TAD)
        RepositorioLecturas repositorio = new RepositorioLecturas();
        for (LecturaSensor lectura : procesador.getLecturasValidas()) {
            repositorio.agregar(lectura);
        }

        System.out.println("\n--- ESTADO DEL REPOSITOIO DINÁMICO (TAD) ---");
        System.out.println("Elementos almacenados: " + repositorio.getTamanio());
        System.out.println("Capacidad actual del arreglo: " + repositorio.getCapacidad());

        // 2. Cargar lecturas válidas en la Matriz Estación x Hora
        String[] estaciones = {"EST-001", "EST-002", "EST-003", "EST-004"};
        MatrizEstacionHora matriz = new MatrizEstacionHora(estaciones);

        for (int i = 0; i < repositorio.getTamanio(); i++) {
            LecturaSensor l = repositorio.obtener(i);
            // Extraer la hora del timestamp (ejemplo formato: "YYYY-MM-DD HH:mm")
            try {
                String[] partesFechaHora = l.getTimestamp().split(" ");
                if (partesFechaHora.length > 1) {
                    int hora = Integer.parseInt(partesFechaHora[1].split(":")[0]);
                    matriz.registrarMedicion(l.getIdSensor(), hora, l.getPm25());
                }
            } catch (Exception e) {
                System.out.println("No se pudo parsear la hora para el registro: " + l.getIdSensor());
            }
        }

        // Mostrar la matriz en consola
        matriz.mostrarMatriz();
    }
}