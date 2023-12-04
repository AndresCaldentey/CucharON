package Presentation.Adapters;

public class Pais {
    private int imageP;
    private String nombreP;

    public Pais(int imageP, String nombreP) {
        this.imageP = imageP;
        this.nombreP = nombreP;
    }

    public int getImage() {
        return imageP;
    }

    public void setImage(int imageP) {
        this.imageP = imageP;
    }

    public String getNombre() {
        return nombreP;
    }

    public void setNombre(String nombreP) {
        this.nombreP = nombreP;
    }
}
