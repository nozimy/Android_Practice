package com.example.nozimy.task1;

public final class Image {

    String name;
    String link;
    String description;
    int defaultImage;

    Image(String link, String name, String desc, int defaultImage){
        this.link = link;
        this.name = name;
        this.description = desc;
        this.defaultImage = defaultImage;
    }
}
