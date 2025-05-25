import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inicio inicioFrame = new Inicio();
            inicioFrame.setVisible(true);
        });
    }
}

