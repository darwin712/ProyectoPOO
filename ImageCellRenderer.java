import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

//Clase personalizada para renderizar imagenes en un JTable

public class ImageCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            //Creacion de la imagen
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setIcon((ImageIcon) value);
            return label;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
