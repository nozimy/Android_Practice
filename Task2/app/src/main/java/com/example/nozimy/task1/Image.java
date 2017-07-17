package com.example.nozimy.task1;

import java.io.Serializable;

public final class Image implements Serializable{

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
