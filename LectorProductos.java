import java.io.*;
import java.util.ArrayList;

public class LectorProductos {
    public static ArrayList<Productos> leerProductosDesdeArchivo(String archivoRuta) {
        ArrayList<Productos> lista = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoRuta))) {
            while (true) {
                try {
                    Productos producto = (Productos) ois.readObject();
                    lista.add(producto);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + archivoRuta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
