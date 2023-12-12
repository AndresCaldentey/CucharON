package Presentation.Adapters;

import android.graphics.drawable.Drawable;

public class Sabor {
    private int imagenSabor;
    private String tituloSabor;
    private int fraseSabor;

    public int getImagenSabor() {
        return imagenSabor;
    }

    public void setImagenSabor(int imagenSabor) {
        this.imagenSabor = imagenSabor;
    }

    public String getTituloSabor() {
        return tituloSabor;
    }

    public void setTituloSabor(String tituloSabor) {
        this.tituloSabor = tituloSabor;
    }

    public int getFraseSabor() {
        return fraseSabor;
    }

    public void setFraseSabor(int fraseSabor) {
        this.fraseSabor = fraseSabor;
    }

    public Sabor(int imagenSabor, String tituloSabor, int fraseSabor) {
        this.imagenSabor = imagenSabor;
        this.tituloSabor = tituloSabor;
        this.fraseSabor = fraseSabor;
    }
}