package quindiofresh.notificacion;

public class NotificacionSMS implements Notificacion {
    @Override
    public void enviar(String destino, String asunto, String cuerpo) {
        System.out.println("Enviando SMS a " + destino);
        System.out.println("Mensaje: " + cuerpo);
    }
}