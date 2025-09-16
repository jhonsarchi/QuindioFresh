package quindiofresh.pago;

public class PagoTarjetaCredito implements Pago {
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago con TARJETA DE CRÉDITO. Monto: $" + monto);
        return true;
    }

    @Override
    public String obtenerReferenciaPago() {
        return "REF-TC-" + System.currentTimeMillis();
    }
}