import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


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
}

public class Cajero extends JFrame {
    private JTextField txtCodigoProducto, txtCantidad;
    private JButton btnAgregar, btnRealizarVenta, btnCorteCaja;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private java.util.List<Productos> inventario;
    private java.util.List<Venta> ventasActuales;
    private double totalVenta;

    public Cajero() {
        setTitle("Interfaz Cajero");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inventario = cargarInventario();
        ventasActuales = new ArrayList<>();
        totalVenta = 0.0;

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel panelEntrada = new JPanel();
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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

    private java.util.List<Productos> cargarInventario() {
        java.util.List<Productos> lista = new ArrayList<>();
        File archivo = new File("productos.dat");
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo productos.dat");
            return lista;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj;
            while (true) {
                try {
                    obj = ois.readObject();
                    if (obj instanceof Productos) {
                        lista.add((Productos) obj);
                    }
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar inventario: " + ex.getMessage());
        }

        return lista;
    }

    private void guardarInventario() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
            for (Productos p : inventario) {
                oos.writeObject(p);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar inventario: " + ex.getMessage());
        }
    }

    private Productos buscarProductoPorCodigo(String codigo) {
        for (Productos p : inventario) {
            if (p.getId().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    private void agregarProductoVenta() {
        String codigo = txtCodigoProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el código del producto.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida, ingresa un número entero.");
            return;
        }

        Productos producto = buscarProductoPorCodigo(codigo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        int existencias;
        try {
            existencias = Integer.parseInt(producto.getExistencias());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en existencias del producto.");
            return;
        }

        if (cantidad > existencias) {
            JOptionPane.showMessageDialog(this, "No hay suficientes existencias. Disponibles: " + existencias);
            return;
        }

        double precioUnitario;
        try {
            precioUnitario = Double.parseDouble(producto.getCantidad());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en precio del producto.");
            return;
        }

        double totalProducto = precioUnitario * cantidad;
        Venta venta = new Venta(producto.getId(), producto.getNombre(), cantidad, precioUnitario, totalProducto, new Date());
        ventasActuales.add(venta);

        modeloTabla.addRow(new Object[] {
            venta.getIdProducto(),
            venta.getNombreProducto(),
            venta.getCantidad(),
            String.format("$%.2f", venta.getPrecioUnitario()),
            String.format("$%.2f", venta.getTotal())
        });

        totalVenta += totalProducto;
        lblTotal.setText(String.format("Total: $%.2f", totalVenta));

        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtCodigoProducto.requestFocus();
    }

    private void realizarVenta() {
        if (ventasActuales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos agregados para vender.");
            return;
        }

        for (Venta v : ventasActuales) {
            Productos p = buscarProductoPorCodigo(v.getIdProducto());
            if (p != null) {
                int existenciasActuales = Integer.parseInt(p.getExistencias());
                int nuevasExistencias = existenciasActuales - v.getCantidad();
                p.setExistencias(String.valueOf(nuevasExistencias));
            }
        }

        guardarInventario();
        guardarVentas(ventasActuales);

        JOptionPane.showMessageDialog(this, "Venta realizada con éxito. Total a pagar: $" + String.format("%.2f", totalVenta));

        modeloTabla.setRowCount(0);
        ventasActuales.clear();
        totalVenta = 0.0;
        lblTotal.setText("Total: $0.00");
    }

    private void guardarVentas(List<Venta> nuevasVentas) {
        List<Venta> todas = new ArrayList<>();

        File archivo = new File("ventas.dat");
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                while (true) {
                    try {
                        Object obj = ois.readObject();
                        if (obj instanceof Venta) {
                            todas.add((Venta) obj);
                        }
                    } catch (EOFException eof) {
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        todas.addAll(nuevasVentas);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            for (Venta v : todas) {
                oos.writeObject(v);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void generarCorteCaja() {
        File archivo = new File("ventas.dat");
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No hay ventas registradas para corte de caja.");
            return;
        }

        List<Venta> todas = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Venta) {
                        todas.add((Venta) obj);
                    }
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        double totalDia = 0.0;
        try (PrintWriter writer = new PrintWriter("corte_de_caja.txt")) {
            writer.println("CORTE DE CAJA");
            writer.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.println("----------------------------------------");
            for (Venta v : todas) {
                writer.printf("%s - %s - Cant: %d - $%.2f - $%.2f\n",
                        v.getIdProducto(),
                        v.getNombreProducto(),
                        v.getCantidad(),
                        v.getPrecioUnitario(),
                        v.getTotal());
                totalDia += v.getTotal();
            }
            writer.println("----------------------------------------");
            writer.printf("TOTAL DEL DÍA: $%.2f\n", totalDia);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al generar corte de caja: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Corte de caja generado en corte_de_caja.txt");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cajero().setVisible(true));
    }
}
