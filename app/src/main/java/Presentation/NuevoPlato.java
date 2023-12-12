package Presentation;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.example.cucharon.R;

import java.io.IOException;

import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;

public class NuevoPlato extends Fragment{
    Service servicio;
    ImageView imagenPlato;
    EditText textoNombre;
    Button siguienteB;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private OnDataPassListener dataPassListener;
    boolean noTieneImagen = true;

    public NuevoPlato() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        imagenPlato = view.findViewById(R.id.imagenPlato);
        siguienteB = view.findViewById(R.id.siguienteB);
        textoNombre = view.findViewById(R.id.textoNombre);

        siguienteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textoNombre.getText().toString().equals("") || noTieneImagen) {
                    mostrarAlerta();
                } else {
                    sendNombreToActivity(textoNombre.getText().toString());
                    getParentFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new AddProcedencia()).commit();

                }
            }
        });

        imagenPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, REQUEST_IMAGE_CAPTURE);

            }

        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnDataPassListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nuevo_plato, container, false);
    }

    private void sendImagenToActivity(String data) {
        DataObject dataObject = new DataObject("imagen", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void sendNombreToActivity(String data) {
        DataObject dataObject = new DataObject("nombre", data);
        dataPassListener.onDataPass(dataObject);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se ha obtenido resultado de la actividad seleccionar imagen
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), data.getData());
                // Ahora 'bitmap' contiene la imagen en formato Bitmap y puedes usarlo como desees.
            } catch (IOException e) {
                e.printStackTrace();
                // Maneja la excepción apropiadamente
            }
            noTieneImagen = false;
            imagenPlato.setImageBitmap(bitmap);
            sendImagenToActivity(servicio.imagenToString(((BitmapDrawable) imagenPlato.getDrawable()).getBitmap()));
        }
    }

    private void mostrarAlerta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Error");
        builder.setMessage("¡Debes incluir un nombre y una imagen!");

        // Configurar botón positivo (Aceptar)
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en Aceptar
                dialog.dismiss(); // Cierra la alerta
            }
        });

        // Mostrar la alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
