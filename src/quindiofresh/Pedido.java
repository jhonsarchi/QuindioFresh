package quindiofresh;

import quindiofresh.envio.Envio;
import quindiofresh.notificacion.Notificacion;
import quindiofresh.pago.Pago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String idPedido;
    private LocalDateTime fechaCreacion;
    private Cliente cliente;
    private List<ItemPedido> items;
    private String direccionEnvio;
    private String notasEntrega;
    private String codigoDescuento;
    private Pago pago;
    private Envio envio;
    private Notificacion notificacion;
    private String estado;

    private Pedido(Builder builder) {
        this.idPedido = "PED-" + System.currentTimeMillis();
        this.fechaCreacion = LocalDateTime.now();
        this.cliente = builder.cliente;
        this.items = builder.items;
        this.direccionEnvio = builder.direccionEnvio;
        this.notasEntrega = builder.notasEntrega;
        this.codigoDescuento = builder.codigoDescuento;
        this.estado = "CREADO";
    }

    public static class Builder {
        private Cliente cliente;
        private List<ItemPedido> items = new ArrayList<>();
        private String direccionEnvio;
        private String notasEntrega;
        private String codigoDescuento;

        public Builder(Cliente cliente) {
            this.cliente = cliente;
        }

        public Builder agregarItem(ItemPedido item) {
            items.add(item);
            return this;
        }

        public Builder conDireccionEnvio(String direccion) {
            this.direccionEnvio = direccion;
            return this;
        }

        public Builder conNotasEntrega(String notas) {
            this.notasEntrega = notas;
            return this;
        }

        public Builder conCodigoDescuento(String codigo) {
            this.codigoDescuento = codigo;
            return this;
        }

        public Pedido construir() {
            if (cliente == null) {
                throw new IllegalArgumentException("El cliente es obligatorio.");
            }
            if (items.isEmpty()) {
                throw new IllegalArgumentException("Debe agregar al menos un producto al pedido.");
            }
            if (direccionEnvio == null || direccionEnvio.trim().isEmpty()) {
                throw new IllegalArgumentException("La dirección de envío es obligatoria.");
            }
            return new Pedido(this);
        }
    }

    public List<ItemPedido> obtenerItems() {
        return items;
    }

    public void establecerPago(Pago pago) {
        this.pago = pago;
    }

    public void establecerEnvio(Envio envio) {
        this.envio = envio;
    }

    public void establecerNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public double calcularSubtotalProductos() {
        double subtotal = 0.0;
        for (ItemPedido item : items) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    public double calcularCostoEnvio() {
        if (envio != null && direccionEnvio != null && !direccionEnvio.isEmpty()) {
            return envio.calcularCosto();
        }
        return 0.0;
    }

    public double calcularTotal() {
        double subtotal = calcularSubtotalProductos();

        // Aplicar descuento
        if (codigoDescuento != null && codigoDescuento.startsWith("DESC")) {
            try {
                int porcentaje = Integer.parseInt(codigoDescuento.substring(4));
                if (porcentaje >= 1 && porcentaje <= 100) {
                    subtotal = subtotal - (subtotal * porcentaje / 100.0);
                }
            } catch (NumberFormatException e) {
                // Ignorar códigos inválidos
            }
        }

        return subtotal + calcularCostoEnvio();
    }

    public boolean procesarPagoYFinalizar() {
        double total = calcularTotal();
        if (pago != null && pago.procesarPago(total)) {
            if (notificacion != null) {
                notificacion.enviar(cliente.getCorreo(),
                        "Confirmación de Pedido",
                        "Su pedido ha sido procesado con éxito. Total: $" + total);
            }
            this.estado = "COMPLETADO";
            return true;
        }
        this.estado = "FALLIDO";
        return false;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public String getNotasEntrega() {
        return notasEntrega != null ? notasEntrega : "Ninguna";
    }

    public String getCodigoDescuento() {
        return codigoDescuento != null ? codigoDescuento : "Ninguno";
    }

    public Envio getEnvio() {
        return envio;
    }

    public String getEstado() {
        return estado;
    }
}