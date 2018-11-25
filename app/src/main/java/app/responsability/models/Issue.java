package app.responsability.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Issue implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String picture;

    private Double latitude;

    private Double longitude;

    private Boolean hasBeenUpvoted;

    private Boolean hasBeenDownvoted;

    private UserProfile createdByUser;

    private Integer upvotes;

    private Integer downvotes;

    private List<Comment> comments;

    private Boolean isArchived;

    private Date createdAt;

    public Issue() {}

    public Issue(String title, String description, String picture, Double latitude, Double longitude) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getHasBeenUpvoted() {
        return hasBeenUpvoted;
    }

    public void setHasBeenUpvoted(Boolean hasBeenUpvoted) {
        this.hasBeenUpvoted = hasBeenUpvoted;
    }

    public Boolean getHasBeenDownvoted() {
        return hasBeenDownvoted;
    }

    public void setHasBeenDownvoted(Boolean hasBeenDownvoted) {
        this.hasBeenDownvoted = hasBeenDownvoted;
    }

    public UserProfile getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserProfile createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
