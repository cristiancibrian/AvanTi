package mx.avanti.siract.common;

import java.util.List;

public class Tab {

    private String title;
    private String content;
    List<DetalleReporte> detallesReporte;

    public Tab() {
    }

    public Tab(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DetalleReporte> getDetallesReporte() {
        return detallesReporte;
    }

    public void setDetalleReporte(List<DetalleReporte> detallesReporte) {
        this.detallesReporte = detallesReporte;
    }
}
