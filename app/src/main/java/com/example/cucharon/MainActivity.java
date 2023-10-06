package com.example.cucharon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.sql.Connection;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;


public class MainActivity extends AppCompatActivity {

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        Thread hilo = new Thread(() -> {

            new UsuarioRepository(SingletonConnection.getSingletonInstance()).guardar(
                    new Usuario("Andres","pepe2", "pepe2" ,456)
            );

        });
        hilo.start();

    }
}