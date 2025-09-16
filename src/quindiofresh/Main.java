package quindiofresh;

import quindiofresh.envio.*;
import quindiofresh.notificacion.*;
import quindiofresh.pago.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Catalogo catalogo = Catalogo.obtenerInstancia();

        // Mostrar catálogo
        System.out.println("=== Catálogo de Productos ===");
        for (Producto p : catalogo.obtenerProductos()) {
            System.out.println(p);
        }

        // Crear cliente (validación obligatoria)
        System.out.println("\n=== Crear Cliente ===");
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
        } while (nombre.trim().isEmpty());

        String correo;
        do {
            System.out.print("Correo: ");
            correo = scanner.nextLine();
        } while (correo.trim().isEmpty());

        String telefono;
        do {
            System.out.print("Teléfono: ");
            telefono = scanner.nextLine();
        } while (telefono.trim().isEmpty());

        Cliente cliente = new Cliente(nombre, correo, telefono);

        // Selección de productos
        System.out.println("\n=== Seleccionar productos (ingrese código y cantidad, 'fin' para terminar) ===");
        Pedido.Builder builder = new Pedido.Builder(cliente);
        while (true) {
            System.out.print("Código de producto: ");
            String codigo = scanner.nextLine();
            if (codigo.equalsIgnoreCase("fin")) break;
            Producto producto = catalogo.buscarProducto(codigo);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }
            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            builder.agregarItem(new ItemPedido(producto, cantidad));
        }

        // Dirección obligatoria
        String direccion;
        do {
            System.out.print("\nIngrese dirección de envío: ");
            direccion = scanner.nextLine();
        } while (direccion.trim().isEmpty());
        builder.conDireccionEnvio(direccion);

        // Notas (opcional)
        System.out.print("Ingrese notas especiales para la entrega (o Enter si no aplica): ");
        String notas = scanner.nextLine();
        if (!notas.trim().isEmpty()) {
            builder.conNotasEntrega(notas);
        }

        // Descuento (opcional)
        System.out.print("Ingrese código de descuento (o Enter si no aplica): ");
        String descuento = scanner.nextLine();
        if (!descuento.trim().isEmpty()) {
            builder.conCodigoDescuento(descuento);
        }

        Pedido pedido = builder.construir();

        // Pago
        System.out.println("\n=== Seleccionar método de pago ===");
        System.out.println("1. Tarjeta de Crédito");
        System.out.println("2. PSE");
        int opcionPago = Integer.parseInt(scanner.nextLine());
        Pago pago = (opcionPago == 1)
                ? new CreadorPagoTarjetaCredito().crearPago()
                : new CreadorPagoPse().crearPago();
        pedido.establecerPago(pago);

        // Envío
        System.out.println("\n=== Seleccionar tipo de envío ===");
        System.out.println("1. Envío Estándar ($7.000)");
        System.out.println("2. Envío Express ($15.000)");
        int opcionEnvio = Integer.parseInt(scanner.nextLine());
        Envio envio = (opcionEnvio == 1)
                ? new CreadorEnvioEstandar().crearEnvio()
                : new CreadorEnvioExpress().crearEnvio();
        pedido.establecerEnvio(envio);

        // Notificación
        System.out.println("\n=== Seleccionar notificación ===");
        System.out.println("1. Email");
        System.out.println("2. SMS");
        int opcionNotif = Integer.parseInt(scanner.nextLine());
        Notificacion notificacion = (opcionNotif == 1)
                ? new CreadorNotificacionEmail().crearNotificacion()
                : new CreadorNotificacionSMS().crearNotificacion();
        pedido.establecerNotificacion(notificacion);

        // Procesar
        pedido.procesarPagoYFinalizar();

        // Resumen
        System.out.println("\n=== Detalles del Pedido ===");
        System.out.println("ID: " + pedido.getIdPedido());
        System.out.println("Fecha: " + pedido.getFechaCreacion());
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Dirección: " + pedido.getDireccionEnvio());
        System.out.println("Notas: " + pedido.getNotasEntrega());
        System.out.println("Descuento: " + pedido.getCodigoDescuento());

        System.out.println("\n--- Resumen de costos ---");
        System.out.println("Subtotal productos: $" + pedido.calcularSubtotalProductos());
        System.out.println("Costo envío: $" + pedido.calcularCostoEnvio());

        // Mostrar descuento si aplica
        if (pedido.getCodigoDescuento() != null && !pedido.getCodigoDescuento().isEmpty()) {
            String codigo = pedido.getCodigoDescuento();
            if (codigo.startsWith("DESC")) {
                try {
                    int porcentaje = Integer.parseInt(codigo.substring(4));
                    System.out.println("Descuento aplicado: " + porcentaje + "%");
                } catch (NumberFormatException e) {
                    System.out.println("Código de descuento inválido.");
                }
            }
        }

        System.out.println("Total a pagar: $" + pedido.calcularTotal());
        System.out.println("\nEstado final: " + pedido.getEstado());
    }
}