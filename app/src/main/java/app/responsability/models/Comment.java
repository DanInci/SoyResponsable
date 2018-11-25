package app.responsability.models;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private Long id;

    private String content;

    private UserProfile commentedByUser;

    private Date createdAt;

    private Date editedAt;

    public Comment() {}

    public Comment(Long id, String content, UserProfile commentedByUser, Date createdAt, Date editedAt) {
        this.id = id;
        this.content = content;
        this.commentedByUser = commentedByUser;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserProfile getCommentedByUser() {
        return commentedByUser;
    }

    public void setCommentedByUser(UserProfile commentedByUser) {
        this.commentedByUser = commentedByUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }
}
