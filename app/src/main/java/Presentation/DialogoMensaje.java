package Presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoMensaje extends DialogFragment {
    public String mensaje;
    public DialogoMensaje(String mensaje) { this.mensaje = mensaje; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mensaje);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                respuesta.confirmar(DialogoMensaje.this);
                dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                respuesta.cancelar(DialogoMensaje.this);
                dismiss();
            }
        });
        return builder.create();
    }

    public interface Respuestas {
        public void confirmar(DialogFragment dialog);
        public void cancelar(DialogFragment dialog);
    }
    private Respuestas respuesta;
    public void procesarRespuesta(Respuestas r) {
        respuesta = r;
    }
}
