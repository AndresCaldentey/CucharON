package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ProductoCategoria {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "idProducto")
    int idProducto;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombre_categoria;

    public ProductoCategoria(){}
    public ProductoCategoria(int idProducto, String nombre_categoria) {
        this.idProducto = idProducto;
        this.nombre_categoria = nombre_categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
