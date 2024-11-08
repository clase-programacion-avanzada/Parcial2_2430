package com.example;

import com.example.model.Compra;
import com.example.model.Tienda;
import com.example.model.Usuario;
import com.example.model.producto.Musica;
import com.example.model.producto.Pelicula;
import com.example.model.producto.Producto;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        //Prueba del parcial

        List<Producto> productosACargar = List.of(
            new Pelicula(10, 15000, "Evil Dead", "R"),
            new Musica(100, 1000, "A dios le pido", "Juanes", "Pop"),
            new Pelicula(8, 18000, "Inception", "B15"),
            new Musica(50, 1200, "Shape of You", "Ed Sheeran", "Pop"),
            new Pelicula(15, 22000, "The Matrix", "R"),
            new Musica(75, 1500, "Blinding Lights", "The Weeknd", "Synthwave"),
            new Pelicula(5, 13000, "Toy Story", "A"),
            new Musica(200, 800, "Bohemian Rhapsody", "Queen", "Rock"),
            new Pelicula(20, 25000, "Interstellar", "B"),
            new Musica(90, 1100, "Rolling in the Deep", "Adele", "Soul")
        );

        //Crea fechas para cada edad requerida
        LocalDate hoy = LocalDate.now();

        LocalDate hoyHaceDiezAnhos = hoy.minus(Period.ofYears(10)); // fecha para clasificación A
        LocalDate hoyHaceCatorceAnhos = hoy.minus(Period.ofYears(14)); //fecha para clasificicación B
        LocalDate hoyHaceDieciseisAnhos = hoy.minus(Period.ofYears(16)); // fecha para clasificación B15
        LocalDate hoyHaceVeinteAnhos = hoy.minus(Period.ofYears(20));

        //Crear Usuarios de prueba
        List<Usuario> usuariosACargar = List.of(
            new Usuario("user1@example.com", hoyHaceDiezAnhos, "User One"),
            new Usuario("user2@example.com", hoyHaceCatorceAnhos, "User Two"),
            new Usuario("user3@example.com", hoyHaceDieciseisAnhos, "User Three"),
            new Usuario("user4@example.com", hoyHaceDiezAnhos, "User Four"),
            new Usuario("user5@example.com", hoyHaceCatorceAnhos, "User Five"),
            new Usuario("user6@example.com", hoyHaceVeinteAnhos, "User Six")
        );

        Tienda tienda = new Tienda(productosACargar, usuariosACargar);

        //Probar punto 1
        System.out.println("""
            Probando punto 1: La tienda debe continuar teniendo 
            la información luego de guardar y cargar el archivo binario
            """);
        try {
            tienda.guardarInventario();
            tienda.leerInventario();
            System.out.println("tienda cargada con éxito");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


        System.out.println();

        //Tienda lanza una excepción si al agregar un usuario, este ya existe
        System.out.println("Probando a agregar un usuario existente");
        try {
            tienda.agregarUsuario("user1@example.com", hoyHaceDieciseisAnhos, "Existing user");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        //Tienda agrega un usuario exitosamente
        System.out.println("Agregando usuario no existente");
        tienda.agregarUsuario("user7@example.com", hoyHaceCatorceAnhos, "New User");
        System.out.println("Usuario agregado a la tienda");

        //Agrega compras a los usuarios
        tienda.agregarProductoAUsuario("user1@example.com", "Evil Dead", 3);
        tienda.agregarProductoAUsuario("user1@example.com", "A dios le pido", 2);
        tienda.agregarProductoAUsuario("user1@example.com", "Toy Story", 1);
        tienda.agregarProductoAUsuario("user2@example.com", "The Matrix", 3);
        tienda.agregarProductoAUsuario("user2@example.com", "A dios le pido", 2);
        tienda.agregarProductoAUsuario("user2@example.com", "A dios le pido", 2);
        tienda.agregarProductoAUsuario("user2@example.com", "Rolling in the Deep", 1);

        //genera reporte
        try {
            tienda.generarReporteCompras("Musica");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Remueve de una compra los elementos por clasificación
        Usuario primerUsuario = tienda.getUsuarios().get(0);
        Compra compraPrimerUsuario = primerUsuario.getCompra();
        compraPrimerUsuario.removerElementosPorClasificacion("R", primerUsuario)
            .stream()
            .forEach(producto -> System.out.println(producto.getTitulo()));


    }
}