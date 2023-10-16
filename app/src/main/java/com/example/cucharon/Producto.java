package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Producto {

    @DatabaseField(id = true)
    int idProducto;
    @DatabaseField
    String nombre;
    @DatabaseField
    String contenido;

    @DatabaseField
    Float precio;

    @DatabaseField
    String imagen;

    @DatabaseField
    String direccion_recogida;

    @DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuario_publicador;

    @DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuario_comprador;

    List<Ingrediente> ingredientes;

    public Producto(){}

    public Producto(int idProducto, String nombre, String contenido, Float precio, String imagen, String direccion_recogida, String usuario_publicador) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.contenido = contenido;
        this.precio = precio;
        this.imagen = imagen;
        this.direccion_recogida = direccion_recogida;
        this.usuario_publicador = usuario_publicador;
        ingredientes = new ArrayList<>();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion_recogida() {
        return direccion_recogida;
    }

    public void setDireccion_recogida(String direccion_recogida) {
        this.direccion_recogida = direccion_recogida;
    }

    public String getUsuario_publicador() {
        return usuario_publicador;
    }

    public void setUsuario_publicador(String usuario_publicador) {
        this.usuario_publicador = usuario_publicador;
    }

    public String getUsuario_comprador() {
        return usuario_comprador;
    }

    public void setUsuario_comprador(String usuario_comprador) {
        this.usuario_comprador = usuario_comprador;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}


