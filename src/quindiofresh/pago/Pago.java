package quindiofresh.pago;

public interface Pago {
    boolean procesarPago(double monto);
    String obtenerReferenciaPago();
}