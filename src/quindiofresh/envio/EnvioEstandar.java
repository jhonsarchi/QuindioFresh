package quindiofresh.envio;

public class EnvioEstandar implements Envio {
    @Override
    public double calcularCosto() {
        return 7000.0;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío estándar";
    }
}