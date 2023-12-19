package Presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Negocio.Service;

public class IUEditPerfil extends AppCompatActivity {
    Service servicio;
    Usuario usuarioActual;
    ImageView fotoPerfil;
    TextView contadorPalabras;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText nombreEditText,editEdadText, editBiografia, apellidoEdit;
    Bitmap fotodelPerfil;
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

        editBiografia.setText(usuarioActual.getBiografia());
        nombreEditText.setText(usuarioActual.getNombre());

        Date fechaNacimiento = usuarioActual.getEdad();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaStr = formato.format(fechaNacimiento);
        editEdadText.setText(fechaStr);
        apellidoEdit.setText(usuarioActual.getApellido());

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
        if(isNullEditText(nombreEditText, "Nombre")
                || isNullEditText(apellidoEdit, "Apellido")
                || isNullEditText(editEdadText, "Edad")
                || isNullEditText(editBiografia, "Biografía")){
        }else if(!servicio.isValidDate(editEdadText.getText().toString())) {
            createDialog("La fecha no se ajusta al formato 30/10/2002");
        }else{
            Usuario usuarioActualizado = new Usuario(usuarioActual.getEmail(),
                    nombreEditText.getText().toString(),apellidoEdit.getText().toString(),
                    usuarioActual.getContraseña(), usuarioActual.getDireccion(),
                    editBiografia.getText().toString(),getDateFromEditText(), usuarioActual.getTlf(), servicio.imagenToString(fotodelPerfil));
            servicio.actualizarUser(usuarioActualizado);
            Intent intent = new Intent(IUEditPerfil.this,Perfil.class);
            intent.putExtra("usuario", usuarioActualizado.getEmail());
            startActivity(intent);
            finish();
        }
    }

    public void clickImagen(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se ha obtenido resultado de la actividad seleccionar imagen
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            fotodelPerfil = null;
            try {
                fotodelPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                // Ahora 'bitmap' contiene la imagen en formato Bitmap y puedes usarlo como desees.
            } catch (IOException e) {
                e.printStackTrace();
                // Maneja la excepción apropiadamente
            }

            fotoPerfil.setImageBitmap(fotodelPerfil);
        }
    }

    public boolean isNullEditText(@NonNull EditText editText, String nombreCampo){
        boolean result = false;

        if(editText.getText().toString().toString().equals("")) {
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
    private Date getDateFromEditText() {
        String dateTimeString = editEdadText.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fechaDate = dateFormat.parse(dateTimeString);
            return fechaDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(IUEditPerfil.this,Perfil.class);
        intent.putExtra("usuario", usuarioActual.getEmail());
        startActivity(intent);
        finish();
    }
}
