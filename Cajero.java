import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        //Volver al anterior frame al cerrar la ventana actual
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Inicio inicioFrame = new Inicio();
                inicioFrame.setVisible(true);
                dispose();
            }
        });

        //Frame
        setTitle("Interfaz Cajero");
        setSize(700, 500);
        setIconImage(new ImageIcon("recursos/iggycafe.png").getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        inventario = cargarInventario();
        productosVentaActual = new ArrayList<>();
        totalVenta = 0.0;
        ultimoIdVenta = obtenerUltimoIdVenta();

        //Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.decode("#735238"));

        //Panel de entrada
        JPanel panelEntrada = new JPanel();
        panelEntrada.setBackground(Color.decode("#735238"));

        //Label - [Codigo]
        JLabel labelID = new JLabel("Código Producto:");
        labelID.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        labelID.setForeground(Color.decode("#FFFFFF"));
        panelEntrada.add(labelID);
        //TextField - [Codigo]
        txtCodigoProducto = new JTextField(10);
        txtCodigoProducto.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        txtCodigoProducto.setBackground(Color.decode("#f8e8ce"));
        txtCodigoProducto.setForeground(Color.decode("#142e3a"));
        txtCodigoProducto.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelEntrada.add(txtCodigoProducto);

        //Label [Cantidad]
        JLabel labelCant = new JLabel("Cantidad:");
        labelCant.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        labelCant.setForeground(Color.decode("#FFFFFF"));
        panelEntrada.add(labelCant);
        //TextField [Cantidad]
        txtCantidad = new JTextField(5);
        txtCantidad.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        txtCantidad.setBackground(Color.decode("#f8e8ce"));
        txtCantidad.setForeground(Color.decode("#142e3a"));
        txtCantidad.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelEntrada.add(txtCantidad);

        //Boton de Agregar
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnAgregar.setBackground(Color.decode("#664c3d"));
        btnAgregar.setForeground(Color.decode("#FFFFFF"));
        btnAgregar.setPreferredSize(new Dimension(110, 30));
        btnAgregar.setBorder(new LineBorder(Color.decode("#f8e8ce"), 1));
        btnAgregar.setFocusPainted(false);
        panelEntrada.add(btnAgregar);

        panel.add(panelEntrada, BorderLayout.NORTH);

        //Tabla
        String[] columnas = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaVentas = new JTable(modeloTabla);
        //Propiedades de la tabla
        tablaVentas.setRowHeight(30);
        tablaVentas.setBackground(Color.decode("#e6ccb2"));
        tablaVentas.setForeground(Color.decode("#142e3a"));
        tablaVentas.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        tablaVentas.getTableHeader().setReorderingAllowed(false);
        tablaVentas.getTableHeader().setBackground(Color.decode("#b08968"));
        tablaVentas.getTableHeader().setForeground(Color.decode("#FFFFFF"));
        tablaVentas.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.getViewport().setBackground(Color.decode("#8c6d54"));
        panel.add(scroll, BorderLayout.CENTER);

        //Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(Color.decode("#735238"));

        //Mostrar total
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTotal.setForeground(Color.decode("#FFFFFF"));
        panelInferior.add(lblTotal);

        //Boton para realizar venta
        btnRealizarVenta = new JButton("Realizar Venta");
        btnRealizarVenta.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnRealizarVenta.setBackground(Color.decode("#f8e8ce"));
        btnRealizarVenta.setForeground(Color.decode("#3c2413"));
        btnRealizarVenta.setPreferredSize(new Dimension(150, 30));
        btnRealizarVenta.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        btnRealizarVenta.setFocusPainted(false);
        panelInferior.add(btnRealizarVenta);

        //Boton para el corte de caja
        btnCorteCaja = new JButton("Corte de Caja");
        btnCorteCaja.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnCorteCaja.setBackground(Color.decode("#f8e8ce"));
        btnCorteCaja.setForeground(Color.decode("#3c2413"));
        btnCorteCaja.setPreferredSize(new Dimension(150, 30));
        btnCorteCaja.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        btnCorteCaja.setFocusPainted(false);
        panelInferior.add(btnCorteCaja);

        //Icono de archivo
        ImageIcon imagenArchivo = new ImageIcon("recursos/file.png");
        Image imagenArchivoR = imagenArchivo.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon iconoArchivo = new ImageIcon(imagenArchivoR);

        //Boton para abrir archivo
        JButton btnAbrirArchivo = new JButton("Abrir", iconoArchivo);
        btnAbrirArchivo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnAbrirArchivo.setBackground(Color.decode("#f8e8ce"));
        btnAbrirArchivo.setForeground(Color.decode("#3c2413"));
        btnAbrirArchivo.setPreferredSize(new Dimension(120, 30));
        btnAbrirArchivo.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        btnAbrirArchivo.setFocusPainted(false);
        panelInferior.add(btnAbrirArchivo);

        //Listener abrir archivo
        btnAbrirArchivo.addActionListener(e -> {
            Musica.getInstance().playSFX("recursos/clicksfx.wav");
            File archivo = new File("corte_de_caja.txt");
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(this, "El archivo 'corte_de_caja.txt' no existe.");
                return;
            }

            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage());
            }
        });

        panel.add(panelInferior, BorderLayout.SOUTH);
        add(panel);

        //Listeners
        btnAgregar.addActionListener(e -> {Musica.getInstance().playSFX("recursos/clicksfx.wav"); agregarProductoVenta();});
        btnRealizarVenta.addActionListener(e -> {Musica.getInstance().playSFX("recursos/kachingsfx.wav"); realizarVenta();});
        btnCorteCaja.addActionListener(e -> {Musica.getInstance().playSFX("recursos/printsfx.wav"); generarCorteCaja();});
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
