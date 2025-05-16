import java.io.Serializable;

import javax.swing.ImageIcon;

////CLASE PARA LOS PRODUCTOS, LAS CLASES HIJAS HEREDAN SUS ATRIBUTOS
public class Productos implements Serializable{

    private String id;
    private String descripcion;
    private ImageIcon imagen; // Atributo para la imagen
    private int cantidad; 

    // Constructor
    public Productos(String id, String descripcion, ImageIcon imagen, int cantidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
