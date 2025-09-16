package quindiofresh.notificacion;

public class CreadorNotificacionSMS extends CreadorNotificacion {
    @Override
    public Notificacion crearNotificacion() {
        return new NotificacionSMS();
    }
}