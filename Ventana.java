import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Ventana{
    public static void main(String[] args) {
        //Logo Iggy
        ImageIcon imagen = new ImageIcon("iggycafe.png");
        Image imagenR = imagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagenR);
        
        //Frame
        JFrame frame = new JFrame("Iggy Cafe");
        frame.setBackground(Color.decode("#735238"));
        frame.setSize(950, 560);
        frame.setIconImage(logo.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Panel Norte
        JPanel panelN = new JPanel();
        panelN.setBackground(Color.decode("#735238"));
        panelN.setLayout(new BoxLayout(panelN, BoxLayout.X_AXIS));
        frame.add(panelN, BorderLayout.NORTH);

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

        //Panel Central
        JPanel panelC = new JPanel();
        panelC.setBackground(Color.decode("#735238"));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
        frame.add(panelC, BorderLayout.CENTER);

        //Tabla
        String[] columnas = {"ID", "Descripcion", "Medida", "Precio", "Imagen"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(tableModel);

        //Colores de la tabla
        tabla.setBackground(Color.decode("#e6ccb2"));
        tabla.setForeground(Color.decode("#FFFFFF"));
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setRowHeight(30);
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
        panelS.setLayout(new BoxLayout(panelS, BoxLayout.X_AXIS));
        frame.add(panelS, BorderLayout.SOUTH);

        //Botones
        JButton agregarBTN = new JButton("Agregar");
        agregarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        agregarBTN.setBackground(Color.decode("#f9f5f3"));
        agregarBTN.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelS.add(agregarBTN);

        JButton modificarBTN = new JButton("Modificar");
        modificarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        modificarBTN.setBackground(Color.decode("#f9f5f3"));
        modificarBTN.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelS.add(modificarBTN);

        JButton eliminarBTN = new JButton("Eliminar");
        eliminarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        eliminarBTN.setBackground(Color.decode("#f9f5f3"));
        eliminarBTN.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelS.add(eliminarBTN);

        //Usar estilo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

    }
}