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
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioComprador")
    private Usuario usuarioComprador;
    @DatabaseField
    private String horaRecogida;
    @DatabaseField
    private String horaPreparacion;
    @DatabaseField
    private int numRaciones;
    @DatabaseField
    private Date diaPreparacion;
    @DatabaseField
    private double direccionLongitud;
    @DatabaseField
    private double direccionLatitud;
    @DatabaseField
    private boolean entregado;
    @DatabaseField
    private int valoracion;
    @DatabaseField
    private String horaReserva;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String contenido, Double precio, String horaRecogida, String horaPreparacion,
                    String imagen, String direccion_recogida, int numRaciones, Date diaPreparacion, Usuario usuario_publicador,
                    double lat, double lon) {
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
        this.direccionLatitud = lat;
        this.direccionLongitud = lon;
        this.entregado = false;
        this.valoracion = -1;
        this.horaReserva = null;
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

    public Date getDiaPreparacion() {
        return diaPreparacion;
    }

    public void setDiaPreparacion(Date diaPreparacion) {
        this.diaPreparacion = diaPreparacion;
    }

    public double getDireccionLongitud() {
        return direccionLongitud;
    }

    public void setDireccionLongitud(double direccionLongitud) {
        this.direccionLongitud = direccionLongitud;
    }

    public double getDireccionLatitud() {
        return direccionLatitud;
    }

    public void setDireccionLatitud(double direccionLatitud) {
        this.direccionLatitud = direccionLatitud;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }
/*
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }*/
}


