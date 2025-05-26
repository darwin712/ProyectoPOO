import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ventana extends JFrame{
    public Ventana() {
        //Volver al anterior frame al cerrar la ventana actual
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Inicio inicioFrame = new Inicio();
                inicioFrame.setVisible(true);
                dispose();
            }
        });

        //Logo Iggy
        ImageIcon imagen = new ImageIcon("recursos/iggycafe.png");
        Image imagenR = imagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagenR);

        setTitle("Admin");
        setBackground(Color.decode("#735238"));
        setSize(950, 560);
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel Norte
        JPanel panelN = new JPanel();
        panelN.setBackground(Color.decode("#735238"));
        panelN.setLayout(new BoxLayout(panelN, BoxLayout.X_AXIS));
        add(panelN, BorderLayout.NORTH);

        //Logotipo label
        JLabel logotipo = new JLabel(logo);
        logotipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelN.add(logotipo);

        //Label
        JLabel inicio = new JLabel("Platillos:");
        inicio.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        inicio.setForeground(Color.decode("#FFFFFF"));
        inicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelN.add(inicio);

        //Barra de busqueda
        JTextField bbusqueda = new JTextField();
        bbusqueda.setPreferredSize(new Dimension(400, 30));
        bbusqueda.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        bbusqueda.setBackground(Color.decode("#f8e8ce"));
        bbusqueda.setForeground(Color.decode("#142e3a"));
        bbusqueda.setBorder(new LineBorder(Color.decode("#3d2111"), 1));

        //Boton para buscar
        JButton buscar = new JButton("Buscar");
        buscar.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        buscar.setBackground(Color.decode("#3c2413"));
        buscar.setForeground(Color.decode("#FFFFFF"));
        buscar.setPreferredSize(new Dimension(200, 30));
        buscar.setFocusPainted(false);
        buscar.setBorder(new LineBorder(Color.decode("#000000"), 1));
        
        //Panel para la barra de busqueda y su boton
        JPanel pbusqueda = new JPanel();
        pbusqueda.setBackground(Color.decode("#735238"));
        pbusqueda.add(bbusqueda);
        pbusqueda.add(buscar);

        //Panel para guardar y centrar la barra de busqueda junto a su boton
        JPanel panelbusqueda = new JPanel();
        panelbusqueda.setBackground(Color.decode("#735238"));
        panelbusqueda.setLayout(new GridBagLayout()); 
        panelbusqueda.add(pbusqueda); 
        panelN.add(panelbusqueda);

        //Panel Central
        JPanel panelC = new JPanel();
        panelC.setBackground(Color.decode("#735238"));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
        add(panelC, BorderLayout.CENTER);

        //Tabla
        String[] columnas = {"ID", "Nombre", "Descripcion", "Precio", "Medida", "Existencias", "Imagen"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(tableModel);

        //Utilizar el image renderer en la columna de imagenes
        tabla.getColumn("Imagen").setCellRenderer(new ImageCellRenderer());

        //Tamaño de la celda imagen
        tabla.getColumn("Imagen").setPreferredWidth(100);
        tabla.getColumn("Imagen").setMaxWidth(100);
        tabla.getColumn("Imagen").setMinWidth(100);
        //Tamaño de la celda ID
        tabla.getColumn("ID").setPreferredWidth(80);
        tabla.getColumn("ID").setMaxWidth(80);
        tabla.getColumn("ID").setMinWidth(80);

        //Propiedades de la tabla
        tabla.setRowHeight(100);
        tabla.setBackground(Color.decode("#e6ccb2"));
        tabla.setForeground(Color.decode("#142e3a"));
        tabla.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setBackground(Color.decode("#b08968"));
        tabla.getTableHeader().setForeground(Color.decode("#FFFFFF"));
        tabla.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.decode("#8c6d54"));
        panelC.add(scroll);

        //Panel Sur
        JPanel panelS = new JPanel();
        panelS.setBackground(Color.decode("#b08968"));
        add(panelS, BorderLayout.SOUTH);

        //Boton de mostrar
        JButton mostrarBTN = new JButton("Mostrar todo");
        mostrarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        mostrarBTN.setBackground(Color.decode("#f8e8ce"));
        mostrarBTN.setForeground(Color.decode("#3c2413"));
        mostrarBTN.setPreferredSize(new Dimension(150, 30));
        mostrarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        mostrarBTN.setFocusPainted(false);
        panelS.add(mostrarBTN);

        //Boton de agregar
        JButton agregarBTN = new JButton("Agregar");
        agregarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        agregarBTN.setBackground(Color.decode("#f8e8ce"));
        agregarBTN.setForeground(Color.decode("#3c2413"));
        agregarBTN.setPreferredSize(new Dimension(150, 30));
        agregarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        agregarBTN.setFocusPainted(false);
        panelS.add(agregarBTN);

        //Boton de modificar
        JButton modificarBTN = new JButton("Modificar");
        modificarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        modificarBTN.setBackground(Color.decode("#f8e8ce"));
        modificarBTN.setForeground(Color.decode("#3c2413"));
        modificarBTN.setPreferredSize(new Dimension(150, 30));
        modificarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        modificarBTN.setFocusPainted(false);
        panelS.add(modificarBTN);

        //Boton de eliminar
        JButton eliminarBTN = new JButton("Eliminar");
        eliminarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        eliminarBTN.setBackground(Color.decode("#f8e8ce"));
        eliminarBTN.setForeground(Color.decode("#3c2413"));
        eliminarBTN.setPreferredSize(new Dimension(150, 30));
        eliminarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        eliminarBTN.setFocusPainted(false);
        panelS.add(eliminarBTN);

        ////////////LISTENERS///////////

        //Listener para el boton de Agregar
        agregarBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Musica.getInstance().playSFX("recursos/clicksfx.wav"); //Sonido de click
                FormularioProducto formulario = new FormularioProducto(Ventana.this);
                formulario.setVisible(true);
                mostrarBTN.doClick();
            }
        });

        //Listener para el boton de Mostrar
        mostrarBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Musica.getInstance().playSFX("recursos/clicksfx.wav"); //Sonido de click
                ArrayList<Productos> productos = LectorProductos.leerProductosDesdeArchivo("productos.dat");
                tableModel.setRowCount(0); // Limpiar tabla

                for (Productos p : productos) {
                    // Cargar y escalar la imagen
                    ImageIcon icono = null;
                    String ruta = p.getRutaImagen();

                    if (ruta != null && !ruta.isEmpty()) {
                        ImageIcon tempIcon = new ImageIcon(ruta);
                        Image img = tempIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        icono = new ImageIcon(img);
                    }

                    Object[] fila = {
                        p.getId(),
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getCantidad(),
                        p.getMedidas(),
                        p.getExistencias(),
                        new ImageIcon(new ImageIcon(p.getRutaImagen()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH))
                    };

                    tableModel.addRow(fila);
                }
            }
        });


        //Listener para el boton de Busqueda
        buscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Musica.getInstance().playSFX("recursos/clicksfx.wav"); //Sonido de click
                String busquedaTexto = bbusqueda.getText().trim().toLowerCase();

                tableModel.setRowCount(0); // Limpiar tabla
                ArrayList<Productos> productos = LectorProductos.leerProductosDesdeArchivo("productos.dat");

                for(Productos p : productos){
                    String nombre = p.getNombre().toLowerCase();

                    if(nombre.contains(busquedaTexto)){
                        // Cargar y escalar la imagen
                        ImageIcon icono = null;
                        String ruta = p.getRutaImagen();
                        if (ruta != null && !ruta.isEmpty()) {
                            ImageIcon tempIcon = new ImageIcon(ruta);
                            Image img = tempIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                            icono = new ImageIcon(img);
                        }

                        Object[] fila = {
                            p.getId(),
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getCantidad(),
                            p.getMedidas(),
                            p.getExistencias(),
                            new ImageIcon(new ImageIcon(p.getRutaImagen()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH))
                        };

                        tableModel.addRow(fila);
                    }
                }

                if(tableModel.getRowCount() == 0){
                    JOptionPane.showMessageDialog(Ventana.this, "No se encontraron resultados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        //Listener para el boton de Eliminar
        eliminarBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Musica.getInstance().playSFX("recursos/clicksfx.wav"); //Sonido de click
                String codigoEliminar = JOptionPane.showInputDialog(
                    null,
                    "Ingresa el ID del producto a eliminar:",
                    "Eliminar producto",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (codigoEliminar == null) {
                    return;
                }

                if (codigoEliminar.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "No se ingresó ningun ID.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    new Catalogo().eliminarProductos(codigoEliminar);
                }

                mostrarBTN.doClick();
            }
        });

        //Listener para el boton de Modificar
        modificarBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Musica.getInstance().playSFX("recursos/clicksfx.wav"); //Sonido de click
                String codigoModificar = JOptionPane.showInputDialog(
                    null,
                    "Ingresa la ID del producto a modificar:",
                    "Modificar producto",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (codigoModificar == null) {
                    return;
                }

                if (codigoModificar.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "No se ingresó ningun ID.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                }

                ArrayList<Productos> productos = LectorProductos.leerProductosDesdeArchivo("productos.dat");
                Productos productoEncontrado = null;

                for (Productos p : productos) {
                    if (p.getId().equalsIgnoreCase(codigoModificar)) {
                        productoEncontrado = p;
                        break;
                    }
                }

                if (productoEncontrado == null) {
                    JOptionPane.showMessageDialog(Ventana.this, 
                    "No se encontró un producto con el ID: " + codigoModificar, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Crear un formulario con los datos ya existentes
                FormularioProducto formulario = new FormularioProducto(Ventana.this, 
                productoEncontrado.getId(),
                productoEncontrado.getNombre(),
                productoEncontrado.getDescripcion(),
                productoEncontrado.getCantidad(),
                productoEncontrado.getMedidas(),
                productoEncontrado.getExistencias(),
                productoEncontrado.getRutaImagen());
                formulario.setVisible(true);

                if (formulario.isConfirmado()) {
                    Productos productoModificado = formulario.getProductoCreado();

                    for (int i = 0; i < productos.size(); i++) {
                        if (productos.get(i).getId().equals(productoModificado.getId())) {
                            productos.set(i, productoModificado);
                            break;
                        }
                    }
                
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
                        for (Productos producto : productos) {
                            oos.writeObject(producto);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    mostrarBTN.doClick();
                }
            }
        });

        //Refrescar productos automaticamente
        mostrarBTN.doClick();

    }
}