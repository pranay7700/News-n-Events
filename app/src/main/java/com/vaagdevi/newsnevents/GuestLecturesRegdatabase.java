package com.vaagdevi.newsnevents;

public class GuestLecturesRegdatabase {

    private String name;
    private String email;
    private String profilepic;
    private Boolean permission;


    public GuestLecturesRegdatabase(String name, String email, String profilepic, Boolean permission) {
        this.name = name;
        this.email = email;
        this.profilepic = profilepic;
        this.permission = permission;
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

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }
}
