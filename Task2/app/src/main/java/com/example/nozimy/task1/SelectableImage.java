package com.example.nozimy.task1;

public class SelectableImage extends Image {
    private boolean isSelected = false;

    public SelectableImage(Image image, boolean isSelected){
        super(image.getLink(), image.getName(), image.getDescription(), image.getDefaultImage());
        this.isSelected = isSelected;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
