package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;

public class ProductoIngrediente {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "idProducto")
    int idProducto;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombre_ingrediente;
}
