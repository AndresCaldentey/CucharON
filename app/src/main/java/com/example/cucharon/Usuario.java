package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import Persistencia.UsuarioRepository;

@DatabaseTable
public class Usuario {


    @DatabaseField(id = true)
    private String usuario;
    @DatabaseField
    private String contraseña;
    @DatabaseField
    private String direccion;
    @DatabaseField
    private String tlf;

    public Usuario() {}

    public Usuario(String usuario, String contraseña, String direccion, String tlf) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.tlf = tlf;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
}
