package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import static com.mammb.jakartaee.starter.domail.example.criteria.Comment.NAME;

@Entity(name = NAME)
public class Comment extends BasicEntity<Comment> {

    public static final String NAME = "COMMENTS";

    private String commentedBy;
    private LocalDateTime commentedOn;
    private String content;

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public LocalDateTime getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(LocalDateTime commentedOn) {
        this.commentedOn = commentedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
