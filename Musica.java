import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Musica {
    private static Musica instance;
    private Clip audioClip;
    private boolean isPlaying;
    private boolean wasPlayedOnce;

    private Musica() {
        isPlaying = false;
        wasPlayedOnce = false;
    }

    public static Musica getInstance() {
        if (instance == null) {
            instance = new Musica();
        }
        return instance;
    }

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

    public void stopMusic() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            isPlaying = false;
        }
    }

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

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean wasPlayedOnce() {
        return wasPlayedOnce;
    }

    public void setWasPlayedOnce(boolean wasPlayedOnce) {
        this.wasPlayedOnce = wasPlayedOnce;
    }
}
