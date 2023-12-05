package Presentation.Adapters;

import java.util.List;

public class Area {
    String nombreArea;
    boolean visible;
    List<Pais> paises;

    public Area(String nombreArea, boolean abierto) {
        this.nombreArea = nombreArea;
        this.visible = abierto;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
    public List<Pais> getPaises() {
        return paises;
    }
}
