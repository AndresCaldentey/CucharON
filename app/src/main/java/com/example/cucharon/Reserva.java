package com.example.cucharon;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Reserva {

    @DatabaseField(generatedId = true)
    private int idReserva;
    @DatabaseField
    private int cantidad;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "idProducto")
    private Producto idProducto;
    @DatabaseField
    private Date horaRecogida;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioComprador")
    private Usuario usuarioComprador;
    @DatabaseField
    private int valoracion;

    public Reserva(int idReserva, int cantidad, Producto idProducto, Date horaRecogida, Usuario usuarioComprador) {
        this.idReserva = idReserva;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.horaRecogida = horaRecogida;
        this.usuarioComprador = usuarioComprador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Usuario getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(Usuario usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    public Date getHoraRecogida() {
        return horaRecogida;
    }

    public void setHoraRecogida(Date horaRecogida) {
        this.horaRecogida = horaRecogida;
    }



    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
