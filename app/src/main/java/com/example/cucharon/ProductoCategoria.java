package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import Negocio.Service;

@DatabaseTable(tableName = "producto_categoria")
public class ProductoCategoria {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "producto")
    private Producto producto;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "categoria")
    private Categoria categoria;

    public ProductoCategoria(){}
    public ProductoCategoria(Producto producto, Categoria categoria) {
        this.producto = producto;
        this.categoria = categoria;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }


    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Producto> getProductosByCategoria(String nombre){
        List<Producto> lista = new ArrayList<>();
        List<ProductoCategoria> listProductoCategorias = Service.getService().getAllProductoCategoria();
        for(ProductoCategoria productoCategoria: listProductoCategorias)
            if(nombre.equals(productoCategoria.getCategoria().getNombre())){
                lista.add(productoCategoria.getProducto());
            }

        return lista;
    }
}
