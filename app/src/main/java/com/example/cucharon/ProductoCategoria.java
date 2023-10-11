package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ProductoCategoria {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "idProducto")
    int idProducto;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombreCategoria;
}
