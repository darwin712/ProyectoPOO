import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Venta implements Serializable {
    private String idVenta;
    private Date fecha;
    private List<ProductoVendido> productos;

    public Venta(String idVenta, Date fecha, List<ProductoVendido> productos) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.productos = productos;
    }

    public String getIdVenta() { return idVenta; }
    public Date getFecha() { return fecha; }
    public List<ProductoVendido> getProductos() { return productos; }

    public double getTotalVenta() {
        return productos.stream().mapToDouble(ProductoVendido::getTotal).sum();
    }
}
