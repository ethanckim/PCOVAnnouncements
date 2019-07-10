package org.pcov.pcovannouncements.DataClass;

/**
 * The image card is a data model for a single image item.
 * Includes data such as uri, dateModified, orientation, dateTaken, Tag, etc.
 */
public class ImageCard {

    private String tag;
    private String imageUrl;

    public ImageCard() {
        //empty constructor needed
    }

    public ImageCard(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        tag = name;
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setName(String name) {
        tag = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
