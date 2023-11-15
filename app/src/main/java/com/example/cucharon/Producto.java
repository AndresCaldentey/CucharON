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
    private int idProducto;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String contenido;
    @DatabaseField
    private Double precio;
    @DatabaseField
    private String imagen = "";
    @DatabaseField
    private String direccionRecogida;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioPublicador")
    private Usuario usuarioPublicador;
    @DatabaseField (foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioComprador")
    private Usuario usuarioComprador;
    @DatabaseField
    private String horaRecogida;
    @DatabaseField
    private String horaPreparacion;
    @DatabaseField
    private int numRaciones;
    @DatabaseField
    private Date diaPreparacion;

    public Producto(){}

    public Producto(int idProducto, String nombre, String contenido, Double precio, String horaRecogida,String horaPreparacion,
                    String imagen, String direccion_recogida,int numRaciones, Date diaPreparacion, Usuario usuario_publicador) {
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

    public Usuario getUsuarioPublicador() {
        return usuarioPublicador;
    }

    public void setUsuarioPublicador(Usuario usuario_publicador) {
        this.usuarioPublicador = usuario_publicador;
    }

    public Usuario getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(Usuario usuario_comprador) {
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


