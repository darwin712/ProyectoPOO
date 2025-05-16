////VENTANA PARA SOLICITAR DATOS DEL PRODUCTO

import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;

public class FormularioProducto extends JDialog {
    private JTextField nombreField, descripcionField, precioField, medidaField, existenciasField;
    private boolean confirmado = false;
    
    public FormularioProducto(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setLayout(new GridLayout(6, 2));
        
        //CAMPOS PARA SOLICITAR LOS DATOS
        nombreField = new JTextField();
        descripcionField = new JTextField();
        precioField = new JTextField();
        medidaField = new JTextField();
        existenciasField = new JTextField();

        add(new JLabel("Nombre:")); add(nombreField);
        add(new JLabel("Descripción:")); add(descripcionField);
        add(new JLabel("Precio:")); add(precioField);
        add(new JLabel("Medida:")); add(medidaField);
        add(new JLabel("Existencias:")); add(existenciasField);

        JButton aceptar = new JButton("Registrar");
        aceptar.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });

        add(aceptar);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public String getNombre() { return nombreField.getText(); }
    public String getDescripcion() { return descripcionField.getText(); }
    public double getPrecio() { return Double.parseDouble(precioField.getText()); }
    public String getMedida() { return medidaField.getText(); }
    public int getExistencias() { return Integer.parseInt(existenciasField.getText()); }
}
