package com.example.cucharon;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import Persistencia.UsuarioRepository;

@DatabaseTable
public class Usuario {


    @DatabaseField(id = true)
    private String nombre;
    @DatabaseField
    private String contraseña;
    @DatabaseField
    private String direccion;
    @DatabaseField
    private int movilNum;

    public Usuario() {}

    public Usuario(String nombre, String contraseña, String direccion, int movilNum) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.movilNum = movilNum;
        this.direccion = direccion;
    }

    public static Usuario getUserByNum(UsuarioRepository usuarioRepository, int num) {
        List<Usuario> users = usuarioRepository.obtenerTodos();
        for (Usuario user : users) {
            if (user.movilNum == num) return user;
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getMovilNum() {
        return movilNum;
    }

    public void setMovilNum(int movilNum) {
        this.movilNum = movilNum;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
