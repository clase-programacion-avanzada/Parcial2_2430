package com.example.model.producto;

public class Musica extends Producto {

    private String artista;
    private String genero;

    public Musica(int cantidad, double precio, String titulo, String artista, String genero) {
        super(cantidad, precio, titulo);
        this.artista = artista;
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    /*
    Punto 2.2: Método que muestra la información relevante de cada tipo de producto
     */
    @Override
    public String mostrarDetalles() {
        return "<Musica>" + "-<\"" + getTitulo() + "\",$" + getPrecio() + "," + getCantidad() + ",\"" + artista + "\",\"" + genero + "\">";
    }
}
