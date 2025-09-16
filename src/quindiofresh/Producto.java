package quindiofresh;

public class Producto {
    private String sku;
    private String nombre;
    private double precio;

    public Producto(String sku, String nombre, double precio) {
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return sku + " - " + nombre + " - $" + precio;
    }
}