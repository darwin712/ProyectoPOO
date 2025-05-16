import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FormularioProducto extends JDialog {
    private JTextField idField, nombreField, descripcionField, precioField, medidaField, existenciasField;
    private boolean confirmado = false;

    private JRadioButton bebidaButton, alimentoButton;
    private ButtonGroup tipoGroup;

    private JButton seleccionarImagenButton;
    private JLabel imagenPreview;
    private String rutaImagenSeleccionada = null;
    private Productos productoCreado = null;

    public FormularioProducto(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setLayout(new GridLayout(9, 2)); // Aumentamos la cuadrícula
        setSize(350, 400);

        // Campos de texto
        idField = new JTextField();
        nombreField = new JTextField();
        descripcionField = new JTextField();
        precioField = new JTextField();
        medidaField = new JTextField();
        existenciasField = new JTextField();

        // Imagen
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
                imagenPreview.setText(""); // Quitamos texto
            }
        });

        // Radio buttons para tipo de producto
        bebidaButton = new JRadioButton("Bebida");
        alimentoButton = new JRadioButton("Alimento");

        tipoGroup = new ButtonGroup();
        tipoGroup.add(bebidaButton);
        tipoGroup.add(alimentoButton);

        // Agregar campos al formulario
        add(new JLabel("ID:")); add(idField);
        add(new JLabel("Nombre:")); add(nombreField);
        add(new JLabel("Descripción:")); add(descripcionField);
        add(new JLabel("Precio:")); add(precioField);
        add(new JLabel("Medida:")); add(medidaField);
        add(new JLabel("Existencias:")); add(existenciasField);

        add(seleccionarImagenButton); add(imagenPreview);

        add(new JLabel("Tipo:"));
        JPanel tipoPanel = new JPanel(new FlowLayout());
        tipoPanel.add(bebidaButton);
        tipoPanel.add(alimentoButton);
        add(tipoPanel);

        // Botón de aceptar
        JButton aceptar = new JButton("Registrar");
        aceptar.addActionListener(e -> {
            confirmado = true;
    
            if(bebidaButton.isSelected()){
                String medidaConUnidad = medidaField.getText() + " litros";
                productoCreado = new Bebidas(
                    idField.getText(), 
                    nombreField.getText(), 
                    descripcionField.getText(),
                    (ImageIcon) imagenPreview.getIcon(),
                    precioField.getText(),
                    medidaConUnidad
                );
            } else if(alimentoButton.isSelected()){
                String medidaConUnidad = medidaField.getText() + " pzs";
                productoCreado = new Alimentos(
                    idField.getText(), 
                    nombreField.getText(), 
                    descripcionField.getText(),
                    (ImageIcon) imagenPreview.getIcon(),
                    precioField.getText(),
                    medidaConUnidad
                );
            }
    
        setVisible(false);  // Cerramos el diálogo
        });
        add(aceptar); add(new JLabel()); // Rellenamos la última celda

        setLocationRelativeTo(parent);
        pack();

    }

}
