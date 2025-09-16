package quindiofresh.notificacion;

public class CreadorNotificacionEmail extends CreadorNotificacion {
    @Override
    public Notificacion crearNotificacion() {
        return new NotificacionEmail();
    }
}