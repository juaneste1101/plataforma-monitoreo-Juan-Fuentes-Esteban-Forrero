package src;

public class LecturaSensor {
    private String idSensor;
    private String timestamp;
    private double temperatura;
    private double humedad;
    private double pm25;
    private boolean esValida;
    private String motivoFalla;

    public LecturaSensor(String idSensor, String timestamp, double temperatura, double humedad, double pm25) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.pm25 = pm25;
        this.esValida = true;
        this.motivoFalla = "OK";
        validarRangosFisicos();
    }

    private void validarRangosFisicos() {
        if (temperatura == -999) {
            this.esValida = false;
            this.motivoFalla = "Sensor desconectado (-999)";
            return;
        }
        if (temperatura < -40 || temperatura > 60) {
            this.esValida = false;
            this.motivoFalla = "Temperatura fuera de rango físico";
            return;
        }

        if (humedad < 0 || humedad > 100) {
            this.esValida = false;
            this.motivoFalla = "Humedad fuera de rango (0-100)";
            return;
        }

        if (pm25 < 0) {
            this.esValida = false;
            this.motivoFalla = "PM2.5 negativo inválido";
            return;
        }
    }

    public boolean isValida() {
        return esValida;
    }

    public String getMotivoFalla() {
        return motivoFalla;
    }

    public String getIdSensor() {
        return idSensor;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getPm25() {
        return pm25;
    }

    @Override
    public String toString() {
        if (esValida) {
            return String.format("[%s] %s - Temp: %.1f°C, Hum: %.1f%%, PM2.5: %.1f", 
                    idSensor, timestamp, temperatura, humedad, pm25);
        } else {
            return String.format("[CUARENTENA] %s %s - Motivo: %s", 
                    idSensor, timestamp, motivoFalla);
        }
    }
}