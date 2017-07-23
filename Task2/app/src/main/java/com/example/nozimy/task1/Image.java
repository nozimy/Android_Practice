package com.example.nozimy.task1;

import java.io.Serializable;

public class Image implements Serializable{

    private String name;
    private String link;
    private String description;
    private int defaultImage;

    Image(String link, String name, String desc, int defaultImage){
        this.link = link;
        this.name = name;
        this.description = desc;
        this.defaultImage = defaultImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Image imageCompare= (Image) obj;
        if(imageCompare.getLink().equals(this.getLink())){
            return true;
        }
        return false;
    }
}
