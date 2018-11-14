package com.jbsx.view.main.entity;

import java.util.List;

/**
 * Created by lijian on 2018/11/10.
 */

public class SpecialAlbums {
    private Album album;
    private List<Celebrities> celebrities;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Celebrities> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(List<Celebrities> celebrities) {
        this.celebrities = celebrities;
    }
}