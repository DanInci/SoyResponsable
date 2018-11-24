package app.responsability.models;

import java.util.List;

import app.responsability.models.component.GoogleLocation;

public class LocationSearchResult {

    private List<GoogleLocation> candidates;

    private String status;

    public LocationSearchResult() {}

    public LocationSearchResult(List<GoogleLocation> candidates, String status) {
        this.candidates = candidates;
        this.status = status;
    }

    public List<GoogleLocation> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<GoogleLocation> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
