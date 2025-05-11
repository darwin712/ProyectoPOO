import javax.swing.*;

public class Ventana{
    public static void main(String[] args) {
        //Frame
        JFrame frame = new JFrame("Iggy Cafe");
        frame.setSize(950, 560);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Panel
        JPanel mainPanel = new JPanel();
        frame.add(mainPanel);

        //Titulo
        ImageIcon logo = new ImageIcon("iggycafe.png");
        JLabel titulo = new JLabel(logo);
        mainPanel.add(titulo);

        frame.setVisible(true);

    }
}