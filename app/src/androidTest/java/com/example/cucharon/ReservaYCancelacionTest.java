package com.example.cucharon;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.List;

import Negocio.Service;

@RunWith(AndroidJUnit4.class)
public class ReservaYCancelacionTest {

    Producto producto;
    Service service = Service.getService();

    @Test
    public void HacerReserva() {


        List<Producto> listaProductos = service.getAllProducto();

        assertFalse(listaProductos.isEmpty());


        for (Producto producto1 : listaProductos) {
            if (producto1.getUsuarioComprador() == null) {
                producto = producto1;

                break;
            }
        }


        Usuario user = new Usuario("a@gmail.com", "usuarioPureba", "apellido", "contraseña", "direccion", 123);

        producto.setUsuarioComprador(user);
        service.actualizarProducto(producto);

        assertNotNull(producto);
        assertNotNull(producto.getUsuarioComprador());

    }

    @Test
    public void cancelarReserva() {

        List<Producto> listaProductos = service.getAllProducto();

        assertFalse(listaProductos.isEmpty());


        for (Producto producto1 : listaProductos) {
            if (producto1.getUsuarioComprador() != null) {
                producto = producto1;
                break;
            }
        }

        assertNotNull(producto);

        producto.setUsuarioComprador(null);
        service.actualizarProducto(producto);

        assertNull(producto.getUsuarioComprador());


    }


}
