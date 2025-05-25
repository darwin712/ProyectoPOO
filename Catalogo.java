////ESTA CLASE CONTIENE LA LISTA DE PRODUCTOS Y SU ADMINISTRACION EN EL ARCHIVO

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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

    public void eliminarProductos(String bcodigo) {
        try {
            // Leer productos del archivo
            ArrayList<Productos> productos = LectorProductos.leerProductosDesdeArchivo("productos.dat");

            // Crear lista nueva sin los productos que coincidan
            ArrayList<Productos> productosFiltrados = new ArrayList<>();
            boolean productoEliminado = false;

            for (Productos p : productos) {
                if (!p.getId().equalsIgnoreCase(bcodigo)) {
                    productosFiltrados.add(p);
                } else {
                    productoEliminado = true;
                }
            }

            // Reescribir el archivo objeto por objeto para mantener el formato
            try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
                for (Productos p : productosFiltrados) {
                    salida.writeObject(p);
                }
            }

            // Mostrar mensajes
            if (productoEliminado) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID: "+bcodigo, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




}
