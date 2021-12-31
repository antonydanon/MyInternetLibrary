package com.ita.u1.internetLibrary.model;

public class PhotoOfBook {
    private byte[] photo;

    public PhotoOfBook(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
