import javax.swing.ImageIcon;

////CLASE HIJA PARA ALIMENTOS, SE MIDEN EN CANTIDAD DE PIEZAS
public class Alimentos extends Productos {

    private String piezas;
    private String numero = "piezas";

    // Constructor
    public Alimentos(String id, String descripcion, ImageIcon imagen, int cantidad, String piezas) {
        super(id, descripcion, imagen, cantidad); 
        this.piezas = piezas; 
    }

    // Getters y setters

    public String getPiezas() {
        return piezas;
    }

    public void setPiezas(String piezas) {
        this.piezas = piezas;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
