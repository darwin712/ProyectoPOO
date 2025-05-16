import javax.swing.ImageIcon;

////CLASE HIJA PARA BEBIDAS, SE MIDEN EN LITROS
public class Bebidas extends Productos {

    private String litros;
    private String medida = "litros";

    // Constructor
    public Bebidas(String id, String descripcion, ImageIcon imagen, int cantidad, String litros) {
        super(id, descripcion, imagen, cantidad); 
        this.litros = litros; 
    }

    // Getter para litros
    public String getLitros() {
        return litros;
    }

    // Setter para litros
    public void setLitros(String litros) {
        this.litros = litros;
    }

    // Getter para medida
    public String getMedida() {
        return medida;
    }

    // Setter para medida
    public void setMedida(String medida) {
        this.medida = medida;
    }

}
