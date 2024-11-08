package com.example.model.producto;

import java.io.Serializable;

public abstract class Producto implements Serializable {

    private int cantidad;
    private double precio;
    private String titulo;

    public Producto(int cantidad, double precio, String titulo) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.titulo = titulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void reducirCantidad(int cantidad) {

        if (cantidad > this.cantidad) {
            throw new IllegalArgumentException("Inventario insuficiente de " + this.titulo);
        }

        this.cantidad -= cantidad;
    }

    public abstract String mostrarDetalles();
}
