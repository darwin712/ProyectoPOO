////ESTA CLASE CONTIENE LA LISTA DE PRODUCTOS Y SU ADMINISTRACION EN EL ARCHIVO

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Catalogo {
    ArrayList<Productos> listaProductos = new ArrayList<>();
    File archivo = new File("productos.dat");

   public void agregarProducto(Productos producto){
        try {
            if(archivo.exists()){
                ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("productos.dat"));
                listaProductos = (ArrayList<Productos>) entrada.readObject();
                entrada.close();
            }

            listaProductos.add(producto);

            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("productos.dat"));
            salida.writeObject(listaProductos);
            salida.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();  
        }
    }


    public ArrayList<Productos> cargarProductos() {
    if(archivo.exists()){
        try(ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))){
            listaProductos = (ArrayList<Productos>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    } else {
        listaProductos = new ArrayList<>();
    }
    return listaProductos;
    }

    public void eliminarProductos(String bnombre) {
        try {
            ArrayList<Productos> productos = LectorProductos.leerProductosDesdeArchivo("productos.dat");

            // Crear lista nueva sin los productos que coincidan
            ArrayList<Productos> productosFiltrados = new ArrayList<>();

            for (Productos p : productos) {
                if (!p.getNombre().toLowerCase().contains(bnombre.toLowerCase())) {
                    productosFiltrados.add(p);
                }
            }

            // Reescribir el archivo objeto por objeto para mantener el formato
            try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
                for (Productos p : productosFiltrados) {
                    
                    salida.writeObject(p);
                    
                }
            }

            // Mostrar mensaje de éxito al usuario
            JOptionPane.showMessageDialog(null, "Producto(s) eliminado(s) correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }



}
