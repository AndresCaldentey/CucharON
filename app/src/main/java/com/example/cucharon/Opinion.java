package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Opinion {
    @DatabaseField(id = true)
    private int id_opinion;
    @DatabaseField
    private String comentario;
    @DatabaseField
    private int valoracion;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "evaluador")
    private Usuario evaluador;
    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "usuarioEvaluado")
    private Usuario usuarioEvaluado;

    public Opinion(){}
    public Opinion(int id_opinion, String comentario, int valoracion, Usuario evaluador, Usuario usuarioEvaluado) {
        this.id_opinion = id_opinion;
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.evaluador = evaluador;
        this.usuarioEvaluado = usuarioEvaluado;
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
        return evaluador;
    }

    public void setUsuarioOrigen(Usuario evaluador) {
        this.evaluador = evaluador;
    }

    public Usuario getUsuarioDestino() {
        return usuarioEvaluado;
    }

    public void setUsuarioDestino(Usuario usuarioEvaluado) { this.usuarioEvaluado = usuarioEvaluado; }
}
