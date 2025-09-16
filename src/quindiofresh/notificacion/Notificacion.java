package quindiofresh.notificacion;

public interface Notificacion {
    void enviar(String destino, String asunto, String cuerpo);
}