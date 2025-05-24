import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Cajero extends JFrame {
    private JTextField txtCodigoProducto, txtCantidad;
    private JButton btnAgregar, btnRealizarVenta, btnCorteCaja;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private List<Productos> inventario;
    private List<ProductoVendido> productosVentaActual;
    private double totalVenta;
    private int ultimoIdVenta = 0;

    public Cajero() {
        setTitle("Interfaz Cajero");
        setSize(700, 500);
        setIconImage(new ImageIcon("iggycafe.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        inventario = cargarInventario();
        productosVentaActual = new ArrayList<>();
        totalVenta = 0.0;
        ultimoIdVenta = obtenerUltimoIdVenta();

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel panelEntrada = new JPanel();
        panelEntrada.setBackground(Color.decode("#735238"));

        panelEntrada.add(new JLabel("Código Producto:"));
        txtCodigoProducto = new JTextField(10);
        panelEntrada.add(txtCodigoProducto);

        panelEntrada.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField(5);
        panelEntrada.add(txtCantidad);

        btnAgregar = new JButton("Agregar");
        panelEntrada.add(btnAgregar);

        panel.add(panelEntrada, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaVentas = new JTable(modeloTabla);
        panel.add(new JScrollPane(tablaVentas), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: $0.00");
        panelInferior.add(lblTotal);

        btnRealizarVenta = new JButton("Realizar Venta");
        panelInferior.add(btnRealizarVenta);

        btnCorteCaja = new JButton("Corte de Caja");
        panelInferior.add(btnCorteCaja);

        panel.add(panelInferior, BorderLayout.SOUTH);
        add(panel);

        btnAgregar.addActionListener(e -> agregarProductoVenta());
        btnRealizarVenta.addActionListener(e -> realizarVenta());
        btnCorteCaja.addActionListener(e -> generarCorteCaja());
    }

    private List<Productos> cargarInventario() {
        List<Productos> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productos.dat"))) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof Productos) lista.add((Productos) obj);
            }
        } catch (EOFException ignored) {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando inventario: " + e.getMessage());
        }
        return lista;
    }

    private void guardarInventario() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
            for (Productos p : inventario) oos.writeObject(p);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error guardando inventario: " + e.getMessage());
        }
    }

    private Productos buscarProducto(String codigo) {
        return inventario.stream()
            .filter(p -> p.getId().equalsIgnoreCase(codigo))
            .findFirst()
            .orElse(null);
    }

    private void agregarProductoVenta() {
        String codigo = txtCodigoProducto.getText().trim();
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }

        Productos p = buscarProducto(codigo);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
            return;
        }

        int stock = Integer.parseInt(p.getExistencias());
        if (cantidad > stock) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente: " + stock);
            return;
        }

        double precio = Double.parseDouble(p.getCantidad());
        ProductoVendido prod = new ProductoVendido(p.getId(), p.getNombre(), cantidad, precio);
        productosVentaActual.add(prod);

        modeloTabla.addRow(new Object[]{
            prod.getIdProducto(),
            prod.getNombreProducto(),
            prod.getCantidad(),
            String.format("$%.2f", prod.getPrecioUnitario()),
            String.format("$%.2f", prod.getTotal())
        });

        totalVenta += prod.getTotal();
        lblTotal.setText(String.format("Total: $%.2f", totalVenta));
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }

    private void realizarVenta() {
        if (productosVentaActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en la venta.");
            return;
        }

        for (ProductoVendido pv : productosVentaActual) {
            Productos p = buscarProducto(pv.getIdProducto());
            int nuevoStock = Integer.parseInt(p.getExistencias()) - pv.getCantidad();
            p.setExistencias(String.valueOf(nuevoStock));
        }

        guardarInventario();
        ultimoIdVenta++;
        Venta venta = new Venta(String.valueOf(ultimoIdVenta), new Date(), new ArrayList<>(productosVentaActual));
        guardarVentas(Collections.singletonList(venta));

        JOptionPane.showMessageDialog(this, "Venta realizada con éxito. Total: $" + String.format("%.2f", totalVenta));

        productosVentaActual.clear();
        modeloTabla.setRowCount(0);
        totalVenta = 0;
        lblTotal.setText("Total: $0.00");
    }

    private void guardarVentas(List<Venta> nuevas) {
        List<Venta> todas = new ArrayList<>();
        File archivo = new File("ventas.dat");

        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                while (true) {
                    try {
                        Object obj = ois.readObject();
                        if (obj instanceof Venta) todas.add((Venta) obj);
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        todas.addAll(nuevas);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ventas.dat"))) {
            for (Venta v : todas) oos.writeObject(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int obtenerUltimoIdVenta() {
        int maxId = 0;
        File archivo = new File("ventas.dat");

        if (!archivo.exists()) return 0;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Venta) {
                        Venta v = (Venta) obj;
                        try {
                            int id = Integer.parseInt(v.getIdVenta());
                            if (id > maxId) maxId = id;
                        } catch (NumberFormatException ignored) {}
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

    private void generarCorteCaja() {
        File archivo = new File("ventas.dat");
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No hay ventas registradas.");
            return;
        }

        List<Venta> todas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Venta) todas.add((Venta) obj);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double totalDia = 0.0;
        try (PrintWriter writer = new PrintWriter("corte_de_caja.txt")) {
            writer.println("CORTE DE CAJA");
            writer.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.println("===========================================");

            for (Venta v : todas) {
                writer.printf("Venta ID: %s | Fecha: %s\n", v.getIdVenta(), v.getFecha());
                for (ProductoVendido p : v.getProductos()) {
                    writer.printf("  %s - %s - x%d - $%.2f - $%.2f\n",
                        p.getIdProducto(), p.getNombreProducto(),
                        p.getCantidad(), p.getPrecioUnitario(), p.getTotal());
                }
                writer.printf("  TOTAL VENTA: $%.2f\n\n", v.getTotalVenta());
                totalDia += v.getTotalVenta();
            }

            writer.println("===========================================");
            writer.printf("TOTAL DEL DÍA: $%.2f\n", totalDia);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al generar el corte de caja.");
        }

        JOptionPane.showMessageDialog(this, "Corte de caja generado: corte_de_caja.txt");
    }
}
