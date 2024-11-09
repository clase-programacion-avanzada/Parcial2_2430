package com.example.model.producto;

public class Pelicula extends Producto {

    //Clasificación de la película puede ser A (para todo público), B (mayores de 13), B15 (mayores de 15) y R (mayores de 18).
    private String clasificacion;

    public Pelicula(int cantidad, double precio, String titulo, String clasificacion) {
        super(cantidad, precio, titulo);
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public int getEdadClasificacion(String clasificacion) {
        switch (clasificacion) {
            case "B":
                return 13;
            case "B15":
                return 15;
            case "R":
                return 18;
            default:
                return 0;
        }
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /*
     Punto 2.1: Método que muestra la información relevante de cada tipo de producto
     */
    @Override
    public String mostrarDetalles() {
        return "<Pelicula>" + "-<\"" + getTitulo() + "\",$" + getPrecio() + "," + getCantidad() + ",\"" + clasificacion + "\">";
    }
}
