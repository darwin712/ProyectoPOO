import Extras.ImageCellRenderer;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Cajero extends JFrame{
    //Logo Iggy
    ImageIcon imagen = new ImageIcon("Extras/iggycafe.png");
    Image imagenR = imagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon logo = new ImageIcon(imagenR);

    public Cajero(){
        setTitle("Personal de Cajas");
        getContentPane().setBackground(Color.decode("#735238"));
        setSize(950, 560);
        setIconImage(logo.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel Norte
        JPanel panelN = new JPanel();
        panelN.setBackground(Color.decode("#735238"));
        panelN.setLayout(new FlowLayout());
        add(panelN, BorderLayout.NORTH);

        //Label
        JLabel label1 = new JLabel("Ventas:");
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        label1.setForeground(Color.decode("#FFFFFF"));
        panelN.add(label1);

        //Panel Central
        JPanel panelC = new JPanel();
        panelC.setBackground(Color.decode("#735238"));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
        add(panelC, BorderLayout.CENTER);

        //Tabla
        String[] columnas = {"ID", "Nombre", "Cantidad", "Precio", "Total", "Imagen"};
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

        //Botones
        JButton agregarBTN = new JButton("Agregar");
        agregarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        agregarBTN.setBackground(Color.decode("#f8e8ce"));
        agregarBTN.setForeground(Color.decode("#3c2413"));
        agregarBTN.setPreferredSize(new Dimension(150, 30));
        agregarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelS.add(agregarBTN);

        JButton modificarBTN = new JButton("Modificar");
        modificarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        modificarBTN.setBackground(Color.decode("#f8e8ce"));
        modificarBTN.setForeground(Color.decode("#3c2413"));
        modificarBTN.setPreferredSize(new Dimension(150, 30));
        modificarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelS.add(modificarBTN);

        JButton eliminarBTN = new JButton("Eliminar");
        eliminarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        eliminarBTN.setBackground(Color.decode("#f8e8ce"));
        eliminarBTN.setForeground(Color.decode("#3c2413"));
        eliminarBTN.setPreferredSize(new Dimension(150, 30));
        eliminarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelS.add(eliminarBTN);

        JButton ventaBTN = new JButton("Realizar Venta");
        ventaBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        ventaBTN.setBackground(Color.decode("#f8e8ce"));
        ventaBTN.setForeground(Color.decode("#3c2413"));
        ventaBTN.setPreferredSize(new Dimension(180, 30));
        ventaBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelS.add(ventaBTN);

        //Usar estilo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);

    }
}
