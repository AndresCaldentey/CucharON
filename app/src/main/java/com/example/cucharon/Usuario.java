package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import Persistencia.UsuarioRepository;

@DatabaseTable
public class Usuario {
    @DatabaseField(id = true)
    private String email;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String apellido;
    @DatabaseField
    private String contraseña;
    @DatabaseField
    private String direccion;
    @DatabaseField
    private int tlf;
    @DatabaseField
    private String foto;
    @DatabaseField
    private String biografia;
    @DatabaseField
    private int cantidadValoracion;
    @DatabaseField
    private int valoracion;

    public Usuario() {}

    public Usuario(String email, String nombre, String apellido, String contraseña, String direccion, int tlf) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public int getTlf() {
        return tlf;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public int getCantidadValoracion() {
        return cantidadValoracion;
    }

    public void setCantidadValoracion(int cantidadValoracion) {
        this.cantidadValoracion = cantidadValoracion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
