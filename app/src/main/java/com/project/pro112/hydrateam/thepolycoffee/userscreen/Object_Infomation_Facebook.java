package com.project.pro112.hydrateam.thepolycoffee.userscreen;

import java.io.Serializable;

/**
 * Created by The Dark on 22-Nov-2017.
 */

public class Object_Infomation_Facebook implements Serializable {
    private String id, first_name, name, email, gender, birthday;

    public Object_Infomation_Facebook() {
    }

    public Object_Infomation_Facebook(String id, String first_name, String name, String email, String gender, String birthday) {
        this.id = id;
        this.first_name = first_name;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
