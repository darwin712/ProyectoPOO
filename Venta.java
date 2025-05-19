import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Venta implements Serializable {
    private String idProducto;
    private String nombreProducto;
    private int cantidadVendida;
    private double precioUnitario;
    private double total;
    private String fechaHora;

    public Venta(String idProducto, String nombreProducto, int cantidadVendida, double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
        this.total = cantidadVendida * precioUnitario;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters
    public String getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidadVendida() { return cantidadVendida; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotal() { return total; }
    public String getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        return fechaHora + " | ID: " + idProducto + " | " + nombreProducto +" x" + cantidadVendida + " @ $" + precioUnitario +" = $" + total;
    }
}