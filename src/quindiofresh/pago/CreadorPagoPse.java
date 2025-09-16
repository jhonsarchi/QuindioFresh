package quindiofresh.pago;

public class CreadorPagoPse extends CreadorPago {
    @Override
    public Pago crearPago() {
        return new PagoPse();
    }
}