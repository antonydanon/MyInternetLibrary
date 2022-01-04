package com.ita.u1.internetLibrary.model;

public class Author {
    private String name;
    private byte[] photoOfAuthor;
    private int authorId;

    public Author(String name, byte[] photoOfAuthor, int authorId) {
        this.name = name;
        this.photoOfAuthor = photoOfAuthor;
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhotoOfAuthor() {
        return photoOfAuthor;
    }

    public void setPhotoOfAuthor(byte[] photoOfAuthor) {
        this.photoOfAuthor = photoOfAuthor;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}