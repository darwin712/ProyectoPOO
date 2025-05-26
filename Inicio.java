import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Inicio extends JFrame{
    public Inicio(){
        //Logo Iggy
        ImageIcon imagen = new ImageIcon("recursos/iggycafe.png");
        Image imagenR = imagen.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagenR);

        //Icono de musica
        ImageIcon musicIcon = new ImageIcon("recursos/musicNote.png");
        Image musicIconResized = musicIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon iconoMusica = new ImageIcon(musicIconResized);

        //Frame
        setTitle("Iggy Cafe");
        setBackground(Color.decode("#825d40"));
        setSize(550, 570);
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Boton para la musica
        JButton musicBTN = new JButton("ON", iconoMusica);
        musicBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        musicBTN.setBackground(Color.decode("#a17554"));
        musicBTN.setForeground(Color.decode("#FFFFFF"));
        musicBTN.setPreferredSize(new Dimension(130, 50));
        musicBTN.setFocusPainted(false);

        //Reproducir musica
        if (Musica.getInstance().isPlaying() == false && Musica.getInstance().wasPlayedOnce() == false) {
            Musica.getInstance().playMusic("recursos/iggycafetheme.wav");
            Musica.getInstance().setWasPlayedOnce(true);
        }

        //Listener para alternar musica
        musicBTN.addActionListener(e -> {
            Musica musica = Musica.getInstance();
            if (musica.isPlaying()) {
                musica.stopMusic();
                musicBTN.setText("OFF");
            } else {
                musica.playMusic("recursos/iggycafetheme.wav");
                musicBTN.setText("ON");
            }
        });

        add(musicBTN, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        //Panel esquina superior derecha
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#825d40"));
        topPanel.add(musicBTN, BorderLayout.EAST);

        //Panel norte
        JPanel panelN = new JPanel();
        panelN.setBackground(Color.decode("#825d40"));
        panelN.setLayout(new BorderLayout());
        
        //Imagen del iggy
        JLabel logotipo = new JLabel();
        logotipo.setIcon(logo);
        logotipo.setHorizontalAlignment(SwingConstants.CENTER);
        panelN.add(logotipo, BorderLayout.CENTER);

        //Mensaje de bienvenida
        JLabel label1 = new JLabel("Bienvenido!", SwingConstants.CENTER);
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 34));
        label1.setForeground(Color.decode("#FFFFFF"));
        panelN.add(label1, BorderLayout.SOUTH);

        panelN.add(topPanel, BorderLayout.NORTH);
        add(panelN, BorderLayout.NORTH);

        //Panel para los botones
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.decode("#825d40"));
        panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.Y_AXIS));

        panelBtn.add(Box.createVerticalGlue()); // Espaciado superior  

        //Boton Administrador
        JButton adminBTN = new JButton("Administrador");
        adminBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        adminBTN.setBackground(Color.decode("#f8e8ce"));
        adminBTN.setForeground(Color.decode("#3c2413"));
        adminBTN.setPreferredSize(new Dimension(250, 50));
        adminBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        adminBTN.setFocusPainted(false);
        adminBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBtn.add(adminBTN);

        panelBtn.add(Box.createRigidArea(new Dimension(0, 30))); // Espaciado entre botones

        //Boton cajero
        JButton cajeroBTN = new JButton("Personal de Cajas");
        cajeroBTN.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        cajeroBTN.setBackground(Color.decode("#f8e8ce"));
        cajeroBTN.setForeground(Color.decode("#3c2413"));
        cajeroBTN.setPreferredSize(new Dimension(250, 50));
        cajeroBTN.setBorder(new LineBorder(Color.decode("#3d2111"), 1));
        cajeroBTN.setFocusPainted(false);
        cajeroBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBtn.add(cajeroBTN);

        panelBtn.add(Box.createVerticalGlue()); // Espaciado inferior

        add(panelBtn, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(false);

        //Cambiar al frame de Admin
        adminBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Ventana frameAdmin = new Ventana();
                frameAdmin.setVisible(true);
                frameAdmin.setLocationRelativeTo(null);
                setVisible(false);
                Musica.getInstance().playSFX("recursos/clicksfx.wav");
            }
        });

        //Cambiar al frame de Cajero
        cajeroBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Cajero frameCajero = new Cajero();
                frameCajero.setVisible(true);
                frameCajero.setLocationRelativeTo(null);
                setVisible(false);
                Musica.getInstance().playSFX("recursos/clicksfx.wav");
            }
        });

    }
}