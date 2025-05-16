////ESTA CLASE CONTIENE LA LISTA DE PRODUCTOS Y SU ADMINISTRACION EN EL ARCHIVO

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

}
