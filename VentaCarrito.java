import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentaCarrito implements Serializable {
    private List<Venta> productosVendidos; // lista de productos vendidos en esta venta
    private String fechaHora;

    public VentaCarrito(List<Venta> productosVendidos) {
        this.productosVendidos = productosVendidos;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public List<Venta> getProductosVendidos() {
        return productosVendidos;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fechaHora).append(" | Venta completa:\n");
        double totalVenta = 0;
        for (Venta v : productosVendidos) {
            sb.append("    ").append(v.toString()).append("\n");
            totalVenta += v.getTotal();
        }
        sb.append("    Total de la venta: $").append(String.format("%.2f", totalVenta));
        return sb.toString();
    }
}
