package com.jbsx.view.main.entity;

import java.util.List;

public class SpecialSingles {
    private Single single;
    private List<Celebrities> celebrities;
    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    public List<Celebrities> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(List<Celebrities> celebrities) {
        this.celebrities = celebrities;
    }
}
