import java.io.Serializable;

public class ProductoVendido implements Serializable {
    private String idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;

    public ProductoVendido(String id, String nombre, int cantidad, double precioUnitario) {
        this.idProducto = id;
        this.nombreProducto = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotal() { return cantidad * precioUnitario; }
}
