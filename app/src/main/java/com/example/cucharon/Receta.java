package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Receta {
    @DatabaseField(id = true)
    private int id_receta;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String descripcion;
    @DatabaseField
    private String imagen;
    @DatabaseField
    private String instrucciones;
    @DatabaseField (foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuario_chef")
    private Usuario usuario_chef;

    public Receta(){}

    public Receta(int id_receta, String nombre, String descripcion, String imagen, String instrucciones, Usuario usuario_chef) {
        this.id_receta = id_receta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.instrucciones = instrucciones;
        this.usuario_chef = usuario_chef;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Usuario getUsuario_chef() {
        return usuario_chef;
    }

    public void setUsuario_chef(Usuario usuario_chef) {
        this.usuario_chef = usuario_chef;
    }
}
