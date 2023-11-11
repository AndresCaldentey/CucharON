package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;

@DatabaseTable
public class Producto implements Serializable {

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
    @DatabaseField //(foreign = true/*, canBeNull = false, foreignAutoRefresh = true, /*foreignAutoCreate = true, columnName = "usuarioPublicador"*/)
    String usuarioPublicador;
    @DatabaseField //(foreign = true/*, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioComprador"*/)
    String usuarioComprador;
    @DatabaseField
    String horaRecogida;
    @DatabaseField
    String horaPreparacion;
    @DatabaseField
    int numRaciones;
    @DatabaseField
    Date diaPreparacion;

    //List<Ingrediente> ingredientes;

    public Producto(){}

    public Producto(int idProducto, String nombre, String contenido, Double precio, String horaRecogida,String horaPreparacion,String imagen, String direccion_recogida,int numRaciones, Date diaPreparacion,String usuario_publicador) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.contenido = contenido;
        this.precio = precio;
        this.horaRecogida = horaRecogida;
        this.imagen = imagen;
        this.direccionRecogida = direccion_recogida;
        this.horaPreparacion = horaPreparacion;
        this.numRaciones = numRaciones;
        this.diaPreparacion = diaPreparacion;
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
        return usuarioComprador;
    }

    public void setUsuarioComprador(String usuario_comprador) {
        this.usuarioComprador = usuario_comprador;
    }

    public String getHoraRecogida() {
        return horaRecogida;
    }

    public void setHoraRecogida(String horaRecogida) {
        this.horaRecogida = horaRecogida;
    }

    public int getNumRaciones() {
        return numRaciones;
    }

    public void setNumRaciones(int numRaciones) {
        this.numRaciones = numRaciones;
    }

    public String getHoraPreparacion() {
        return horaPreparacion;
    }

    public void setHoraPreparacion(String horaPreparacion) {
        this.horaPreparacion = horaPreparacion;
    }
    /*
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }*/
}


