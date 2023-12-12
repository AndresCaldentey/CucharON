package Presentation.Adapters;

public class DataObject {
    private String source;
    private String data;

    public DataObject(String source, String data) {
        this.source = source;
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public String getData() {
        return data;
    }
}
