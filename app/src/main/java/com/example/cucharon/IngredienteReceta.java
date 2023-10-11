package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class IngredienteReceta {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "id_receta")
    int id_receta;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombre_ingrediente;

    public IngredienteReceta(){}
    public IngredienteReceta(int id_receta, String nombre_ingrediente) {
        this.id_receta = id_receta;
        this.nombre_ingrediente = nombre_ingrediente;
    }

    public int getId_receta() {
        return id_receta;
    }

    public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public String getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public void setNombre_ingrediente(String nombre_ingrediente) {
        this.nombre_ingrediente = nombre_ingrediente;
    }
}
