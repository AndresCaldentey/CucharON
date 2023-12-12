package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Categoria {
    @DatabaseField(id = true)
    private String nombre;
    @DatabaseField
    private String descripcion;
    @DatabaseField
    private Boolean esPais;

    public Categoria(){}
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;

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

    public Boolean getEsPais() {
        return esPais;
    }
    public void setEsPais(Boolean esPais) {
        this.esPais = esPais;
    }
}
