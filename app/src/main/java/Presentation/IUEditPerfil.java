package Presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.Service;

public class IUEditPerfil extends AppCompatActivity {
    Service servicio;
    Usuario usuarioActual;
    ImageView fotoPerfil;
    TextView contadorPalabras;

    EditText nombreEditText,editEdadText, editBiografia, apellidoEdit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Recoger Datos del Intent
        Intent intent = getIntent();
        String usuarioEmail = intent.getStringExtra("usuario");
        usuarioActual = servicio.getUsuarioByEmail(usuarioEmail);
        nombreEditText = findViewById(R.id.nombreEditText);
        apellidoEdit = findViewById(R.id.apellidoEdit);
        editEdadText = findViewById(R.id.editEdadText);
        editBiografia = findViewById(R.id.editBiografia);
        fotoPerfil = findViewById(R.id.fotoDeditarPerfil);
        contadorPalabras = findViewById(R.id.contadorPalabras);

        if(usuarioActual.getFoto() != null) fotoPerfil.setImageBitmap(servicio.pasarStringAImagen(usuarioActual.getFoto()));
        else { fotoPerfil.setImageResource(R.drawable.martina); }

        nombreEditText.setText(usuarioActual.getNombre());
        apellidoEdit.setText(usuarioActual.getApellido());
        //editEdadText.setText(usuarioActual.getEdad());
        //editBiografia.setText(usuarioActual.getBiografia());
        editBiografia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contadorPalabras.setText(charSequence.length() + " carácteres");
                if(charSequence.length() >= 200) {
                    contadorPalabras.setTextColor(R.color.rojoDis);
                } else {
                    contadorPalabras.setTextColor(R.color.negroDis);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void aceptarClick (View view) {
        if(isNullEditText(nombreEditText, "Nombre")  || isNullEditText(apellidoEdit, "Apellido") || isNullEditText(editEdadText, "Edad")
                || isNullEditText(editBiografia, "Biografía"));
        else if(!servicio.isValidDate(editEdadText.getText().toString())) createDialog("La fecha no se ajusta al formato 30/10/2002");
    }

    public boolean isNullEditText(@NonNull EditText editText, String nombreCampo){
        boolean result = false;

        if(editText.getText().toString() == "") {
           createDialog("El campo " + nombreCampo + " está vacío");
            result = true;
        }
        return result;
    }

    public void createDialog(String error) {
        View alertCustomDialog = LayoutInflater.from(IUEditPerfil.this).inflate(R.layout.custom_dialog,null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(IUEditPerfil.this);
        alertDialog.setView(alertCustomDialog);
        Button aceptarB = alertCustomDialog.findViewById(R.id.aceptarDialogB);
        TextView errorText = alertCustomDialog.findViewById(R.id.errorText);
        errorText.setText(error);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        aceptarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}