package quindiofresh.notificacion;

public class NotificacionEmail implements Notificacion {
    @Override
    public void enviar(String destino, String asunto, String cuerpo) {
        System.out.println("Enviando EMAIL a " + destino);
        System.out.println("Asunto: " + asunto);
        System.out.println("Cuerpo: " + cuerpo);
    }
}