package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class IngredienteReceta {
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "id_receta")
    int idReceta;
    @DatabaseField(id = true,foreign = true, foreignAutoRefresh = true, columnName = "nombre")
    String nombreIngrediente;

}
