package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Producto {

    @DatabaseField(generatedId = true)
    int idProducto;
    @DatabaseField
    String nombre;
    @DatabaseField
    String contenido;
    @DatabaseField
    Double precio;
    @DatabaseField
    String imagen = "";
    @DatabaseField
    String direccionRecogida;
    @DatabaseField //(foreign = true, canBeNull = false, foreignAutoRefresh = true)
    String usuarioPublicador;
    @DatabaseField //(foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuarioComprador;
    @DatabaseField
    String horaRecogida;

    //List<Ingrediente> ingredientes;

    public Producto(){}

    public Producto(int idProducto, String nombre, String contenido, Double precio, String horaRecogia,String imagen, String direccion_recogida, String usuario_publicador) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.contenido = contenido;
        this.precio = precio;
        this.horaRecogida = horaRecogia;
        this.imagen = imagen;
        this.direccionRecogida = direccion_recogida;
        this.usuarioPublicador = usuario_publicador;
        this.usuarioComprador = null;
       // ingredientes = new ArrayList<>();
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccionRecogida() {
        return direccionRecogida;
    }

    public void setDireccionRecogida(String direccion_recogida) {
        this.direccionRecogida = direccion_recogida;
    }

    public String getUsuarioPublicador() {
        return usuarioPublicador;
    }

    public void setUsuarioPublicador(String usuario_publicador) {
        this.usuarioPublicador = usuario_publicador;
    }

    public String getUsuarioComprador() {
        return usuarioPublicador;
    }

    public void setUsuarioComprador(String usuario_comprador) {
        this.usuarioPublicador = usuario_comprador;
    }
/*
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }*/
}


