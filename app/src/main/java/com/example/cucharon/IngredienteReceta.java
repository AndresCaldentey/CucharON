package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class IngredienteReceta {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "id_receta")
    Receta receta;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "nombre_ingrediente")
    Ingrediente nombre_ingrediente;

    public IngredienteReceta(){}
    public IngredienteReceta(Receta receta, Ingrediente nombre_ingrediente) {
        this.receta = receta;
        this.nombre_ingrediente = nombre_ingrediente;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Ingrediente getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public void setNombre_ingrediente(Ingrediente nombre_ingrediente) {
        this.nombre_ingrediente = nombre_ingrediente;
    }
}
