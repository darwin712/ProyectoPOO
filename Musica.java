import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//Clase para poder reproducir musica y sonidos en la aplicacion

public class Musica {
    private static Musica instance; //Instancia unica
    private boolean isPlaying; //La musica esta reproduciendose?
    private boolean wasPlayedOnce; //Se reprodujo al menos 1 vez?
    private Clip audioClip;

    //Constructor para utilizar el Singleton
    private Musica() {
        isPlaying = false;
        wasPlayedOnce = false;
    }

    //Metodo para obtener la instancia unica
    public static Musica getInstance() {
        if (instance == null) {
            instance = new Musica();
        }
        return instance;
    }

    //Metodo para reproducir la musica principal de la aplicacion en bucle
    public void playMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // Reproducir en bucle
            audioClip.start();
            isPlaying = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir el archivo: " + e.getMessage());
        }
    }

    //Metodo para parar la musica
    public void stopMusic() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            isPlaying = false;
        }
    }

    //Metodo para reproducir efectos de sonido exclusivamente (sin bucle)
    public void playSFX(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioStream);
            soundClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir el efecto de sonido: " + e.getMessage());
        }
    }

    //Metodo para verificar si la musica esta sonando
    public boolean isPlaying() {
        return isPlaying;
    }

    //Metodo para verificar si la musica ha sonado aunque sea 1 vez
    public boolean wasPlayedOnce() {
        return wasPlayedOnce;
    }

    //Metodo para establecer el estado de reproduccion
    public void setWasPlayedOnce(boolean wasPlayedOnce) {
        this.wasPlayedOnce = wasPlayedOnce;
    }
}
