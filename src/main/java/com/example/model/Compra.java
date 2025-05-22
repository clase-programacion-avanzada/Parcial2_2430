package com.example.model;

import com.example.model.producto.Pelicula;
import com.example.model.producto.Producto;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Compra implements Serializable {

    private LocalDate fecha;
    private List<Producto> productosCompra;

    public Compra(LocalDate fecha) {
        this.fecha = fecha;
        this.productosCompra = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Producto> getProductosCompra() {
        return new ArrayList<>(productosCompra);
    }


    public Set<String> getDetallesProductosPorTipo(String tipoProducto) {
        Set<String> productosFiltrados = new HashSet<>();

        for (Producto producto : productosCompra) {
            if(producto.getClass().getSimpleName().equals(tipoProducto)) {
                productosFiltrados.add(producto.mostrarDetalles());
            }
        }

        //Usando streams
        Set<String> productosFiltrados2 = productosCompra.stream()
                .filter(producto -> producto.getClass().getSimpleName().equals(tipoProducto))
                .map(Producto::mostrarDetalles)
                .collect(Collectors.toSet());

        return productosFiltrados;
    }

    /*
    Punto 4: método que recibe el título del producto, la tienda y la cantidad de unidades a comprar.
        - Si el producto no existe en la Tienda, debe arrojar una excepción, mencionando la no existencia del producto.
        - Si no hay suficiente inventario del producto para satisfacer la compra, es decir, si se desea comprar una cantidad mayor a la existente en la tienda, lanza una excepción de inventario insuficiente.
        Retorna la cantidad del producto que queda, una vez se ha adicionado ese producto a la compra.
     */

    public int agregarProducto(String titulo, int cantidad, Tienda tienda) {

        Producto producto = tienda.buscarProducto(titulo);
        boolean existeProducto = producto != null;

        if(!existeProducto) {
            throw new IllegalArgumentException("El producto no existe");
        }

        producto.reducirCantidad(cantidad);

        productosCompra.add(producto);

        return producto.getCantidad();

    }

    /*
    Punto 6: método que elimina todos los productos de tipo Película que tengan la clasificación dada como parámetro.
        - Si la compra incluye productos de tipo Película y no cumple con la clasificación requerida
        (es decir, el Usuario, pasado como parámetro, tiene una edad inferior a la clasificación de esa película), ese producto se debe eliminar de la lista de productos de la compra.

        El método debe retornar los elementos actualizados de la compra.
        Cabe mencionar que si la Película tiene como clasificación A, nunca será eliminada. (Nota: puede utilizar el método calcularEdad de la clase Usuario).
     */
    public List<Producto> removerElementosPorClasificacion(String clasificacion, Usuario usuario) {

        int edadUsuario = usuario.calcularEdad();

        List<Producto> productosActualizados = new ArrayList<>();


        for (Producto producto : productosCompra) {
            if(!(producto instanceof Pelicula pelicula)
                || (pelicula.getClasificacion().equals("A")
                    || edadUsuario >= pelicula.getEdadClasificacion(pelicula.getClasificacion()))
            ) {
                    productosActualizados.add(producto);
            }
        }

        //Usando streams
        Predicate<Producto> esClasificacionRequerida = producto ->
            !(producto instanceof Pelicula p)
                || (p.getClasificacion().equals("A") || edadUsuario >= p.getEdadClasificacion(p.getClasificacion()));
        List<Producto> productosActualizados2 = productosCompra.stream()
                .filter(esClasificacionRequerida)
                .toList();

        //Usando removeIf
        /*
        productosCompra.removeIf(producto -> producto instanceof Pelicula pelicula
            && !pelicula.getClasificacion().equals("A")
            && edadUsuario < pelicula.getEdadClasificacion(pelicula.getClasificacion()));

         */


        productosCompra.clear();
        productosCompra.addAll(productosActualizados);

        return productosActualizados;

    }

}
