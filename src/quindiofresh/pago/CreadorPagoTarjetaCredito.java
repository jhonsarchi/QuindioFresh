package quindiofresh.pago;

public class CreadorPagoTarjetaCredito extends CreadorPago {
    @Override
    public Pago crearPago() {
        return new PagoTarjetaCredito();
    }
}