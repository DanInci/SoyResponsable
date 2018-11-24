package app.responsability.models.component;

public class GoogleLocationGeometry {

    private Location location;

    private Viewport viewport;

    public GoogleLocationGeometry() { }

    public GoogleLocationGeometry(Location location, Viewport viewport) {
        this.location = location;
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public class Location {

        private Double lat;

        private Double lng;

        public Location() {}

        public Location(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }

    private class Viewport {

        private Location northeast;

        private Location southwest;

        public Viewport() {}

        public Viewport(Location northeast, Location southwest) {
            this.northeast = northeast;
            this.southwest = southwest;
        }

        public Location getNortheast() {
            return northeast;
        }

        public void setNortheast(Location northeast) {
            this.northeast = northeast;
        }

        public Location getSouthwest() {
            return southwest;
        }

        public void setSouthwest(Location southwest) {
            this.southwest = southwest;
        }
    }
}
