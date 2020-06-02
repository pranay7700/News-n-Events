package com.vaagdevi.newsnevents;

public class WorkshopsRegdatabase {

    public String name, description, image, place, date, time;

    public WorkshopsRegdatabase() {
    }

    public WorkshopsRegdatabase(String name, String description, String image, String place, String date, String time) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.place = place;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
