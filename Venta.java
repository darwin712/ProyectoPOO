import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// Clase Venta
class Venta implements Serializable {
    private String idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private Date fechaVenta;

    public Venta(String idProducto, String nombreProducto, int cantidad, double precioUnitario, double total, Date fechaVenta) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.fechaVenta = fechaVenta;
    }

    public String getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotal() { return total; }
    public Date getFechaVenta() { return fechaVenta; }

    @Override
    public String toString() {
        return fechaVenta + " | ID: " + idProducto + " | " + nombreProducto +" x" + cantidad + " @ $" + precioUnitario +" = $" + total;
    }
}