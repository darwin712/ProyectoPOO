import javax.swing.*;
import javax.swing.border.LineBorder;
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
        frame.add(panelC, BorderLayout.CENTER);

        //Tabla
        String[] columnas = {"ID", "Descripcion", "Medida", "Precio", "Imagen"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(tableModel);

        // Definir la fila con datos que coinciden con las columnas
        Object[] nuevaFila = {"001", "Producto A", "10 unidades", "$100", "imagenA.png"};

        // Agregar la fila al modelo de la tabla
        tableModel.addRow(nuevaFila);


        //Colores de la tabla
        tabla.setBackground(Color.decode("#e6ccb2"));
        tabla.setForeground(Color.decode("#142e3a"));
        tabla.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
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
        frame.add(panelS, BorderLayout.SOUTH);

        //Botones
        JButton mostrarBTN = new JButton("Mostrar todo");
        mostrarBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        mostrarBTN.setBackground(Color.decode("#f8e8ce"));
        mostrarBTN.setForeground(Color.decode("#3c2413"));
        mostrarBTN.setPreferredSize(new Dimension(150, 30));
        mostrarBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        panelS.add(mostrarBTN);

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

        //Usar estilo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

    }
}