import java.awt.*;
import java.io.*;
import javax.swing.*;

public class FormularioProducto extends JDialog {
    private JTextField idField, nombreField, descripcionField, precioField, medidasField, existenciasField;
    private boolean confirmado = false;

    private JRadioButton bebidaButton, alimentoButton;
    private ButtonGroup tipoGroup;

    private JButton seleccionarImagenButton;
    private JLabel imagenPreview;
    private String rutaImagenSeleccionada = null;
    private Productos productoCreado = null;

    public FormularioProducto(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setLayout(new GridLayout(10, 2));
        setSize(350, 400);

        idField = new JTextField();
        nombreField = new JTextField();
        descripcionField = new JTextField();
        precioField = new JTextField();
        medidasField = new JTextField();
        existenciasField = new JTextField();

        seleccionarImagenButton = new JButton("Seleccionar imagen");
        imagenPreview = new JLabel("Sin imagen", SwingConstants.CENTER);
        imagenPreview.setPreferredSize(new Dimension(50, 50));

        seleccionarImagenButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File imagen = fileChooser.getSelectedFile();
                rutaImagenSeleccionada = imagen.getAbsolutePath();

                ImageIcon icono = new ImageIcon(rutaImagenSeleccionada);
                Image imagenEscalada = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                imagenPreview.setIcon(new ImageIcon(imagenEscalada));
                imagenPreview.setText("");
            }
        });

        bebidaButton = new JRadioButton("Bebida");
        alimentoButton = new JRadioButton("Alimento");

        tipoGroup = new ButtonGroup();
        tipoGroup.add(bebidaButton);
        tipoGroup.add(alimentoButton);

        add(new JLabel("ID:")); add(idField);
        add(new JLabel("Nombre:")); add(nombreField);
        add(new JLabel("Descripción:")); add(descripcionField);
        add(new JLabel("Precio:")); add(precioField);
        add(new JLabel("Medida:")); add(medidasField);
        add(new JLabel("Existencias:")); add(existenciasField);

        add(seleccionarImagenButton); add(imagenPreview);

        add(new JLabel("Tipo:"));
        JPanel tipoPanel = new JPanel(new FlowLayout());
        tipoPanel.add(bebidaButton);
        tipoPanel.add(alimentoButton);
        add(tipoPanel);

        JButton aceptar = new JButton("Registrar");
        aceptar.addActionListener(e -> {
            confirmado = true;

            String id = idField.getText();
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            String precio = precioField.getText();
            String medidas = medidasField.getText();  // Reutilizamos este campo para ambos tipos
            String existencias = existenciasField.getText();
            String rutaImagen = rutaImagenSeleccionada;

            if (bebidaButton.isSelected()) {
                productoCreado = new Bebidas(id, nombre, descripcion, rutaImagen, precio, medidas, existencias);
                System.out.println("Bebida creada: " + productoCreado);
            } else if (alimentoButton.isSelected()) {
                productoCreado = new Alimentos(id, nombre, descripcion, rutaImagen, precio, medidas, existencias);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un tipo de producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (bebidaButton.isSelected()) {
                productoCreado = new Bebidas(id, nombre, descripcion, rutaImagen, precio, medidas, existencias);
            } else if (alimentoButton.isSelected()) {
                productoCreado = new Alimentos(id, nombre, descripcion, rutaImagen, precio, medidas, existencias);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un tipo de producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Guardar producto en archivo
            try {
                File archivo = new File("productos.dat");
                boolean append = archivo.exists() && archivo.length() > 0;

                FileOutputStream fos = new FileOutputStream(archivo, true);
                ObjectOutputStream oos = append
                        ? new AppendingObjectOutputStream(fos)
                        : new ObjectOutputStream(fos);

                oos.writeObject(productoCreado);
                oos.close();
                fos.close();

                JOptionPane.showMessageDialog(this, "Producto guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Archivo guardado en: " + archivo.getAbsolutePath());

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }

            setVisible(false);
        });

        add(aceptar); add(new JLabel());

        setLocationRelativeTo(parent);
        pack();
    }

    public Productos getProductoCreado() {
        return productoCreado;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    // Clase para evitar escribir encabezado de nuevo al hacer append
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
