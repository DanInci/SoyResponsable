package app.responsability.models.component;

public class GoogleLocation {

    private String name;

    private GoogleLocationGeometry geometry;

    public GoogleLocation() {}

    public GoogleLocation(String name, GoogleLocationGeometry geometry) {
        this.name = name;
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GoogleLocationGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleLocationGeometry geometry) {
        this.geometry = geometry;
    }
}
