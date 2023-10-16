package Presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        nombre = findViewById(R.id.editTextNombre);
        apellido = findViewById(R.id.editTextApellido);
        telefono = findViewById(R.id.editTextTelefono);
        email = findViewById(R.id.editTextEmail);
        direccion = findViewById(R.id.editTextDireccion);
        btnRegistro = findViewById(R.id.btnRegistro);

    }

    public void onClickRegistrar(View view)
    {
        //Comprobar que no exista un usuario con ese email, etc

        //Y se cierra la actividad
        finish();
    }

}
