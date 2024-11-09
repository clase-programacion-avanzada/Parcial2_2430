package com.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Usuario implements Serializable {

    private String email;
    private LocalDate fechaNacimiento;
    private String nombre;
    //Se marca como transient para que funcione el main, en el parcial no es necesario.
    private transient Tienda tienda;
    private Compra compra;

    public Usuario(String email, LocalDate fechaNacimiento, String nombre, Tienda tienda) {
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.tienda = tienda;

    }

    public Usuario(String email, LocalDate fechaNacimiento, String nombre) {
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
    }

    public Compra getCompra() {
        if (compra == null) {
            compra = new Compra(LocalDate.now());
        }

        return compra;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }


    public int calcularEdad() {

        var fechaActual = LocalDate.now();

        var periodo = fechaNacimiento.until(fechaActual);

        return periodo.getYears();
    }

    public void agregarProductosCompra(String titulo, int cantidad) {

        Compra compra = getCompra();

        compra.agregarProducto(titulo, cantidad, tienda);

    }

}
