package Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;

import Negocio.IService;
import Negocio.Service;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IService service = Service.getService();

        //Comprueba el token de inicio de sesion
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);
        String correo = sharedPreferences.getString("token", "");

        if (!correo.isEmpty()) {
            service.setLoggedUser(service.getUsuarioByEmail(correo));
            Intent intent = new Intent(MainActivity.this, IUsugerencias.class);
            startActivity(intent);
            finish();
        } else
        {
            //El usuario no ha iniciado sesion previamente y se va a login
            Intent intent = new Intent(MainActivity.this, IUlogin.class);
            startActivity(intent);
            finish();
        }

    }

}