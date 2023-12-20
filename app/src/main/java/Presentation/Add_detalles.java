package Presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cucharon.R;

import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;


public class Add_detalles extends Fragment {

    TextView descripcion;
    TextView racion;
    TextView hora1;
    TextView hora2;
    String horaCompleta;
    TextView precio;
    Button siguiente;
    ImageView subirRacion;
    ImageView bajarRacion;
    int contadorRacion;
    private OnDataPassListener dataPassListener;
    String textoError = "";

    public Add_detalles() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descripcion = view.findViewById(R.id.textoDescripcion);
        racion = view.findViewById(R.id.textoRacion);
        hora1 = view.findViewById(R.id.textoHora1);
        hora2 = view.findViewById(R.id.textoHora2);
        precio = view.findViewById(R.id.textoPrecio);
        siguiente = view.findViewById(R.id.siguienteB5);
        subirRacion = view.findViewById(R.id.botonSubirRacion);
        bajarRacion = view.findViewById(R.id.botonBajarRacion);
        contadorRacion = 1;


        siguiente.setOnClickListener(view1 -> {

            if (validarDatos()) {

                horaCompleta = hora1.getText().toString() +" - "+ hora2.getText().toString();
                sendDescripcionToActivity(descripcion.getText().toString());
                sendHoraToActivity(horaCompleta);
                sendPrecioToActivity(precio.getText().toString());
                sendRacionToActivity(racion.getText().toString());

                getParentFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new Add_punto_encuentro()).commit();

            } else {
                mostrarAlerta();
            }

        });

        subirRacion.setOnClickListener(view1 -> {
            contadorRacion++;
            racion.setText(contadorRacion + "");
        });

        bajarRacion.setOnClickListener(view1 -> {
            contadorRacion--;
            racion.setText(contadorRacion + "");
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_detalles, container, false);
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


    private void sendDescripcionToActivity(String data) {
        DataObject dataObject = new DataObject("descripcion", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void sendRacionToActivity(String data) {
        DataObject dataObject = new DataObject("racion", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void sendHoraToActivity(String data) {
        DataObject dataObject = new DataObject("hora", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void sendPrecioToActivity(String data) {
        DataObject dataObject = new DataObject("precio", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void mostrarAlerta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Error");
        builder.setMessage(textoError);

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

    public boolean validarDatos() {
        if (descripcion.getText().toString().equals("")) {
            textoError = "debes incluir una descripción";
            return false;
        } else if (hora1.getText().toString().equals("") || hora2.getText().toString().equals("")) {
            textoError = "Debes incluir una hora de recogida";
            return false;
        }  else if (!validarFormatoHora(hora1.getText().toString()) || !validarFormatoHora(hora2.getText().toString())){
            textoError = "Las horas deben tener el formato correcto";
            return false;
        } else if (Integer.parseInt(racion.getText().toString()) <= 0){
            textoError = "Debes incluir por lo menos una ración";
            return false;
        } else if (precio.getText().toString().equals("")){
            textoError = "Debe introducir un precio";
            return false;
        } else if (Double.parseDouble(precio.getText().toString()) <= 0){
            textoError = "El precio debe ser positivo";
            return false;
        } else if (!compararHoras(hora1.getText().toString(), hora2.getText().toString())){
            textoError = "el rango de hora debe ser correcto";
            return false;
        }


        return true;
    }

    public static boolean compararHoras(String hora1, String hora2) {
        // Dividir las horas y minutos
        String[] partesHora1 = hora1.split(":");
        String[] partesHora2 = hora2.split(":");

        // Obtener las horas y minutos como enteros
        int horas1 = Integer.parseInt(partesHora1[0]);
        int minutos1 = Integer.parseInt(partesHora1[1]);

        int horas2 = Integer.parseInt(partesHora2[0]);
        int minutos2 = Integer.parseInt(partesHora2[1]);

        // Comparar las horas
        if (horas1 < horas2) {
            return true;
        } else if (horas1 == horas2) {
            // Si las horas son iguales, comparar los minutos
            return minutos1 < minutos2;
        } else {
            return false;
        }
    }

    public static boolean validarFormatoHora(String hora) {
        // Utilizar una expresión regular para validar el formato "HH:mm"
        String regexHora = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

        // Comprobar si la hora coincide con la expresión regular
        return hora.matches(regexHora);
    }


}