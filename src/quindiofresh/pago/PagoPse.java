package quindiofresh.pago;

public class PagoPse implements Pago {
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago con PSE. Monto: $" + monto);
        return true;
    }

    @Override
    public String obtenerReferenciaPago() {
        return "REF-PSE-" + System.currentTimeMillis();
    }
}