package app.responsability.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {

    private Long id;

    private String content;

    private UserProfile commentedByUser;

    private LocalDateTime createdAt;

    private LocalDateTime editedAt;

    public Comment() {}

    public Comment(Long id, String content, UserProfile commentedByUser, LocalDateTime createdAt, LocalDateTime editedAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }
}
