package com.example.model;

import com.example.model.producto.Producto;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Tienda implements Serializable {

    private List<Producto> productosTienda;
    private List<Usuario> usuarios;

    public Tienda(List<Producto> productosTienda, List<Usuario> usuarios) {
        this.productosTienda = new ArrayList<>(productosTienda);
        this.usuarios = agregarUsuarios(usuarios);
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Usuario> agregarUsuarios(List<Usuario> usuarios) {
        List<Usuario> usuariosConTienda = usuarios
            .stream()
            .map(usuario ->
                new Usuario(usuario.getEmail(),
                    usuario.getFechaNacimiento(),
                    usuario.getNombre(),
                    this))
            .toList();
        return new ArrayList<>(usuariosConTienda);
    }

    public void leerInventario() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/main/resources/inventario.bin"))) {
            productosTienda = (ArrayList<Producto>) ois.readObject();
            List<Usuario> usuarios = (ArrayList<Usuario>) ois.readObject();
            this.usuarios = agregarUsuarios(usuarios);
        }
    }

    public void agregarProductoAUsuario(String email, String titulo, int cantidad) {

        Usuario usuario = buscarUsuario(email);

        if(usuario == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }

        usuario.agregarProductosCompra(titulo, cantidad);


    }

    /*
    Punto 1: Método que guarda la tienda en un archivo serializado llamado "inventario.bin".
     */
    public void guardarInventario() throws IOException {


        try( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/main/resources/inventario.bin")) ) {
            oos.writeObject(productosTienda);
            oos.writeObject(usuarios);
        }
    }

    /*
    Punto 3: método que busca un producto por su título. Si el producto no se encuentra, debe retornar null.
     */
    public Producto buscarProducto(String titulo) {

        for (Producto producto : productosTienda) {
            if(producto.getTitulo().equals(titulo)) {
                return producto;
            }
        }

        return null;
    }
    /*
    Punto 5: método que, dado el email, la fecha de nacimiento y el nombre del usuario, lo agrega a la lista de usuarios de la Tienda.
        - Si el usuario ya existe (hay un usuario con el mismo email), lanza una excepción.
     */
    public void agregarUsuario(String email, LocalDate fechaNacimiento, String nombre) {

        Usuario usuario = buscarUsuario(email);

        if(usuario != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario(email, fechaNacimiento, nombre, this);
        usuarios.add(nuevoUsuario);

    }

    public Usuario buscarUsuario(String email) {

        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(email)) {
                return usuario;
            }
        }

        return null;

    }


    /*
    Punto 7: recibe como parámetro el tipo de producto (por ejemplo, Música o Película) y genera un reporte en un archivo de texto llamado productos.txt. El reporte debe seguir el siguiente formato:
    <Tipo Producto> - <Toda la información específica del producto>
    Por ejemplo:
    <Música>-<"Luna",3,$10000,"Feid","Reggaeton">
    <Película>-<"Eterno resplandor de una mente sin recuerdos",5,$20000,"B15">
     */
    public void generarReporteCompras(String tipoProducto) throws IOException {

        Set<String> productosAEscribir = new HashSet<>();

        for (Producto producto : productosTienda) {
            if(producto.getClass().getSimpleName().equals(tipoProducto)) {
                productosAEscribir.add(producto.mostrarDetalles());
            }
        }

        Files.write(Path.of("productos.txt"), productosAEscribir);

    }


    public void reducirInventario(String titulo, int cantidad) {

        Producto producto = buscarProducto(titulo);

        int cantidadActual = producto.getCantidad();

        producto.reducirCantidad(cantidad);


    }
}
