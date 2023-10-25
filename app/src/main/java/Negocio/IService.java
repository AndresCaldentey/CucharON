package Negocio;

import android.content.Context;
import android.graphics.Bitmap;

public interface IService {
    boolean validTel(int tel);
    boolean validEmail(String email);
    boolean validPassword(String password);
    boolean passwordMatch(String password1, String password2);
    void ErrorAlert(String errorString, Context contexto);
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);
}
