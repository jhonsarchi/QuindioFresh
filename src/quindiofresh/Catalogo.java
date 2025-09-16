package quindiofresh;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private static Catalogo instancia;
    private List<Producto> productos = new ArrayList<>();

    private Catalogo() {
        productos.add(new Producto("P001", "Manzanas", 3000));
        productos.add(new Producto("P002", "Leche", 2500));
        productos.add(new Producto("P003", "Pan", 2000));
    }

    public static Catalogo obtenerInstancia() {
        if (instancia == null) {
            instancia = new Catalogo();
        }
        return instancia;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }

    public Producto buscarProducto(String sku) {
        for (Producto p : productos) {
            if (p.getSku().equalsIgnoreCase(sku)) {
                return p;
            }
        }
        return null;
    }
}