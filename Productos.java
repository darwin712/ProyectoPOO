// Clase base Productos
import java.io.Serializable;

public class Productos implements Serializable {
    private String id;
    private String nombre;
    private String medidas;
    private String descripcion;
    private String rutaImagen; // Solo guardamos la ruta
    private String cantidad;
    private String existencias;

    public Productos(String id, String nombre, String descripcion, String rutaImagen, String cantidad, String existencias, String medidas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.medidas=medidas;
        this.cantidad = cantidad;
        this.existencias = existencias;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getRutaImagen() { return rutaImagen; }
    public String getCantidad() { return cantidad; }
    public String getExistencias() { return existencias; }
    public String getMedidas(){return medidas;}

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }
    public void setExistencias(String existencias) { this.existencias = existencias; }
    public void setMedidas(String medidas){this.medidas=medidas;}
}   