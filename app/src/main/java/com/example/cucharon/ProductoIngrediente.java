package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ProductoIngrediente {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "idProducto")
    int idProducto;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombre_ingrediente;

    public ProductoIngrediente(){}
    public ProductoIngrediente(int idProducto, String nombre_ingrediente) {
        this.idProducto = idProducto;
        this.nombre_ingrediente = nombre_ingrediente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public void setNombre_ingrediente(String nombre_ingrediente) {
        this.nombre_ingrediente = nombre_ingrediente;
    }
}
