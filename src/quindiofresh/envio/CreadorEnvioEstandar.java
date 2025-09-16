package quindiofresh.envio;

public class CreadorEnvioEstandar extends CreadorEnvio {
    @Override
    public Envio crearEnvio() {
        return new EnvioEstandar();
    }
}