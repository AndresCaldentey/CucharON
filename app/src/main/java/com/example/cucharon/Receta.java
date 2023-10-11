package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Receta {
    @DatabaseField(id = true)
    int id_receta;
    @DatabaseField
    String nombre;
    @DatabaseField
    String descripcion;

    @DatabaseField
    String imagen;

    @DatabaseField
    String instrucciones;
    @DatabaseField (foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuario_chef;

    public Receta(){}

    public Receta(int id_receta, String nombre, String descripcion, String imagen, String instrucciones, String usuario_chef) {
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

    public String getUsuario_chef() {
        return usuario_chef;
    }

    public void setUsuario_chef(String usuario_chef) {
        this.usuario_chef = usuario_chef;
    }
}
