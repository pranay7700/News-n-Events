package com.vaagdevi.newsnevents;

public class GuestLecturesRegdatabase {

    public String name, email, profilepic, date, time, description;

    public GuestLecturesRegdatabase() {
    }

    public GuestLecturesRegdatabase(String name, String email, String profilepic, String date, String time, String description) {
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
