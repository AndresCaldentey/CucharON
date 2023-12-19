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
    private int idProducto;
    @DatabaseField
    private Date horaRecogida;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioComprador")
    private String usuarioComprador;
    @DatabaseField
    private int valoracion;

    public Reserva(int idReserva, int cantidad, int idProducto, Date horaRecogida, String usuarioComprador) {
        this.idReserva = idReserva;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.horaRecogida = horaRecogida;
        this.usuarioComprador = usuarioComprador;
        this.valoracion = -1;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getHoraRecogida() {
        return horaRecogida;
    }

    public void setHoraRecogida(Date horaRecogida) {
        this.horaRecogida = horaRecogida;
    }

    public String getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(String usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
