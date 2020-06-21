package com.vaagdevi.newsnevents;

public class NotificationsRegdatabase {

    public String image, title, description, date;

    public NotificationsRegdatabase() {
    }

    public NotificationsRegdatabase(String image, String title, String description, String date) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
