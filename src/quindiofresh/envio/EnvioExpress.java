package quindiofresh.envio;

public class EnvioExpress implements Envio {
    @Override
    public double calcularCosto() {
        return 15000.0;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío express";
    }
}