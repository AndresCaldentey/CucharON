package com.example.cucharon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class RegistroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

    }

}
