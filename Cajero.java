import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Supongo que esta es tu clase Productos
class Productos implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String cantidad;
    private String existencias;
    private String medida;
    private String precio;

    public Productos(String id, String nombre, String descripcion, String imagen,
                     String cantidad, String existencias, String medida) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.existencias = existencias;
        this.medida = medida;
        this.precio = precio;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getCantidad() { return cantidad; }
    public String getExistencias() { return existencias; }
    public String getMedida() { return medida; }
    public String getPrecio() { return precio; }
    public void setExistencias(String existencias) { this.existencias = existencias; }
    public String getRutaImagen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRutaImagen'");
    }
    public String getRautaImagen() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRautaImagen'");
    }
    public Object getMedidas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMedidas'");
    }
}

// Clase Venta
class Venta {
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
    private JButton btnAgregar, btnRealizarVenta;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private java.util.List<Productos> inventario;  // Lista con todos los productos disponibles
    private java.util.List<Venta> ventasActuales;   // Ventas registradas en la sesión actual

    private double totalVenta;

    public Cajero() {
        setTitle("Interfaz Cajero");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inventario = cargarInventario();  // Método que carga productos.dat
        ventasActuales = new ArrayList<>();
        totalVenta = 0.0;

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Panel superior para ingresar código y cantidad
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

        // Tabla para mostrar productos agregados
        String[] columnas = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Para evitar edición directa
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para mostrar total y botón de venta
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: $0.00");
        panelInferior.add(lblTotal);

        btnRealizarVenta = new JButton("Realizar Venta");
        panelInferior.add(btnRealizarVenta);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        // Acción botón agregar
        btnAgregar.addActionListener(e -> agregarProductoVenta());

        // Acción botón realizar venta
        btnRealizarVenta.addActionListener(e -> realizarVenta());
    }

    private java.util.List<Productos> cargarInventario() {
        java.util.List<Productos> lista = new ArrayList<>();
        // Cargar desde productos.dat con serialización
        File archivo = new File("productos.dat");
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo productos.dat");
            return lista;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = null;
            while (true) {
                try {
                    obj = ois.readObject();
                    if (obj instanceof Productos) {
                        lista.add((Productos)obj);
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

        int cantidad = 0;
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

        int existencias = 0;
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

        double precioUnitario = 0;
        try {
            precioUnitario = Double.parseDouble(producto.getPrecio());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en precio del producto.");
            return;
        }

        double totalProducto = precioUnitario * cantidad;

        // Crear objeto Venta
        Venta venta = new Venta(producto.getId(), producto.getNombre(), cantidad, precioUnitario, totalProducto, new Date());

        // Agregar venta a la lista de ventas actuales
        ventasActuales.add(venta);

        // Agregar fila a la tabla
        Object[] fila = {
                venta.getIdProducto(),
                venta.getNombreProducto(),
                venta.getCantidad(),
                String.format("$%.2f", venta.getPrecioUnitario()),
                String.format("$%.2f", venta.getTotal())
        };
        modeloTabla.addRow(fila);

        // Actualizar total
        totalVenta += totalProducto;
        lblTotal.setText(String.format("Total: $%.2f", totalVenta));

        // Limpiar campos
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtCodigoProducto.requestFocus();
    }

    private void realizarVenta() {
        if (ventasActuales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos agregados para vender.");
            return;
        }

        // Aquí podrías guardar las ventas actuales en un archivo o base de datos
        // También podrías actualizar existencias en inventario

        // Ejemplo simple: Mostrar mensaje y limpiar todo para nueva venta
        JOptionPane.showMessageDialog(this, "Venta realizada con éxito. Total a pagar: $" + String.format("%.2f", totalVenta));

        // Actualizar existencias (disminuir)
        for (Venta v : ventasActuales) {
            Productos p = buscarProductoPorCodigo(v.getIdProducto());
            if (p != null) {
                int existenciasActuales = Integer.parseInt(p.getExistencias());
                int nuevasExistencias = existenciasActuales - v.getCantidad();
                p.setExistencias(String.valueOf(nuevasExistencias));
            }
        }

        // Opcional: guardar inventario actualizado en productos.dat (si quieres que se mantenga)

        // Limpiar tabla y lista ventas
        modeloTabla.setRowCount(0);
        ventasActuales.clear();
        totalVenta = 0.0;
        lblTotal.setText("Total: $0.00");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Cajero().setVisible(true);
        });
    }
}
