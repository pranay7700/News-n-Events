package com.vaagdevi.newsnevents;

public class WorkshopsRegdatabase {

    private String name;
    private String description;
    private String image;
    private boolean permission;


    public WorkshopsRegdatabase(String name, String description, String image, boolean permission) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.permission = permission;
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

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
