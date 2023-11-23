package Presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;
import Negocio.Service;

public class PantallaDeCargaLogin extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // Duración en milisegundos, tiempo que muestra la pantalla
    IService service;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_carga);
        service = Service.getService();
        intent = getIntent();

        // Aquí puedes realizar operaciones de inicialización como acceder a la base de datos
       Usuario usuario = service.getUsuarioByEmail(intent.getStringExtra("usuario"));
        if(usuario != null && service.passwordMatch(usuario.getContraseña(), intent.getStringExtra("contraseña")))
        {
            guardarToken();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Este código se ejecutará después de SPLASH_TIMEOUT
                    Intent intent = new Intent(PantallaDeCargaLogin.this, IUsugerencias.class);
                    startActivity(intent);
                    finish(); // Cierra la actividad de carga para que no vuelva al presionar "Atrás"
                }
            }, SPLASH_TIMEOUT);

        } else{
            showAlertDialog("Error", "El usuario o la contraseña no son correctos", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    // Inicia la nueva actividad después de que se cierre el cuadro de diálogo
                    Intent intent = new Intent(PantallaDeCargaLogin.this, IUlogin.class);
                    startActivity(intent);
                    finish();
                }
            });

        }



    }

    private void guardarToken()
    {
        // Obtener un objeto SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

        // Guardar el token de autenticación
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",intent.getStringExtra("usuario"));
        editor.apply();
        service.setLoggedUser(service.getUsuarioByEmail(intent.getStringExtra("usuario")));

    }



    private void showAlertDialog(String title, String message, DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Código a ejecutar cuando se hace clic en OK
                        dialog.dismiss(); // Cierra el cuadro de diálogo
                    }
                });

        if (onDismissListener != null) {
            builder.setOnDismissListener(onDismissListener);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
