package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Opinion {
    @DatabaseField(id = true)
    int id_opinion;
    @DatabaseField
    String comentario;
    @DatabaseField
    int valoracion;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuario_origen")
    Usuario usuarioOrigen;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuario_destino")
    Usuario usuarioDestino;

    public Opinion(){}
    public Opinion(int id_opinion, String comentario, int valoracion, Usuario usuarioOrigen, Usuario usuarioDestino) {
        this.id_opinion = id_opinion;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
    }

    public int getId_opinion() {
        return id_opinion;
    }

    public void setId_opinion(int id_opinion) {
        this.id_opinion = id_opinion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }
}
