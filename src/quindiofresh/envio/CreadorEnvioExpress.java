package quindiofresh.envio;

public class CreadorEnvioExpress extends CreadorEnvio {
    @Override
    public Envio crearEnvio() {
        return new EnvioExpress();
    }
}