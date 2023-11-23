package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

import Negocio.IService;
import Negocio.Service;

public class IUpagar  extends AppCompatActivity {

    EditText tarjetaEditText;
    EditText caducaEditText;
    EditText cvcEditText;
    EditText titularEditText;
    IService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        tarjetaEditText = findViewById(R.id.tarjetaEditText);
        caducaEditText = findViewById(R.id.caducaEditText);
        cvcEditText = findViewById(R.id.cvcEditText);
        titularEditText = findViewById(R.id.titularEditText);
        service = Service.getService();
    }

    public void clickPagar(View view){
        String tarjeta = String.valueOf(tarjetaEditText.getText());
        String fechaCad = String.valueOf(caducaEditText.getText());
        String cvc = String.valueOf(caducaEditText.getText());
        String titular = String.valueOf(titularEditText.getText());
        if(tarjeta.isEmpty() ){
            service.CrearAlerta("Se ha de introducir la tarjeta de crédito ", this);
        }
        if(fechaCad.isEmpty() ){
            service.CrearAlerta("Se ha de introducir la fecha de caducidad de la tarjeta", this);
        }
        if(cvc.isEmpty() ){
            service.CrearAlerta("Se ha de introducir el CVC de la tarjeta", this);
        }
        if(titular.isEmpty() ){
            service.CrearAlerta("Se ha de intruducir el nombre del titular", this);
        }
    }
}
