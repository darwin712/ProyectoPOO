import java.io.Serializable;

import javax.swing.ImageIcon;

////CLASE PARA LOS PRODUCTOS, LAS CLASES HIJAS HEREDAN SUS ATRIBUTOS
public class Productos implements Serializable{

    private String id;
    private String nombre;
    private String descripcion;
    private ImageIcon imagen; // Atributo para la imagen
    private String cantidad; 

    // Constructor
    public Productos(String id, String nombre, String descripcion, ImageIcon imagen, String cantidad) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
