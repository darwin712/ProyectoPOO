import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroVentas {
    private static final String ARCHIVO_VENTAS = "ventas.dat";

    // Guarda una venta de carrito (venta completa)
    public static void registrarVentaCarrito(VentaCarrito ventaCarrito) {
        try {
            File archivo = new File(ARCHIVO_VENTAS);
            boolean append = archivo.exists() && archivo.length() > 0;

            FileOutputStream fos = new FileOutputStream(archivo, true);
            ObjectOutputStream oos = append
                    ? new AppendingObjectOutputStream(fos)
                    : new ObjectOutputStream(fos);

            oos.writeObject(ventaCarrito);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lee todas las ventas de carrito (ventas completas)
    public static List<VentaCarrito> obtenerVentasCarrito() {
        List<VentaCarrito> ventas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_VENTAS))) {
            while (true) {
                VentaCarrito venta = (VentaCarrito) ois.readObject();
                ventas.add(venta);
            }
        } catch (EOFException e) {
            // fin archivo
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // Genera el archivo de corte con el desglose de ventas completas
    public static void generarCorteDeCaja() {
        List<VentaCarrito> ventas = obtenerVentasCarrito();
        double total = 0;

        try (PrintWriter writer = new PrintWriter("corte_de_caja.txt")) {
            writer.println("***** CORTE DE CAJA *****");
            for (VentaCarrito v : ventas) {
                writer.println(v.toString());
                // Sumar total de cada venta completa
                double totalVenta = 0;
                for (Venta prod : v.getProductosVendidos()) {
                    totalVenta += prod.getTotal();
                }
                total += totalVenta;
            }
            writer.println("---------------------------");
            writer.printf("TOTAL DEL DÍA: $%.2f%n", total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Limpia las ventas (para hacer un corte y empezar nuevo día)
    public static void limpiarVentas() {
        File archivo = new File(ARCHIVO_VENTAS);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    // Clase auxiliar para evitar sobrescribir encabezado
    private static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}
