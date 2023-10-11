package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Opinion {
    @DatabaseField(id = true)
    int idOpinion;
    @DatabaseField
    String comentario;
    @DatabaseField
    int valoracion;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuarioOrigen;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "email")
    String usuarioDestino;

    public Opinion(){}
    public Opinion(int idOpinion, String comentario, int valoracion, String usuarioOrigen, String usuarioDestino) {
        this.idOpinion = idOpinion;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
    }

    public int getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(int idOpinion) {
        this.idOpinion = idOpinion;
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

    public String getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(String usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }
}
