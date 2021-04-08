package com.example.finalyearproject.Models;

public class CommentModel {
    int id;
    String publisher;
    String commentText;
    String uid;
    int recipeId;

    public CommentModel(int id, String publisher, String commentText, String uid, int recipeId) {
        this.id = id;
        this.publisher = publisher;
        this.commentText = commentText;
        this.uid = uid;
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
