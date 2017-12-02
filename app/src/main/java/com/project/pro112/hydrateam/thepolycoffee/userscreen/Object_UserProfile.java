package com.project.pro112.hydrateam.thepolycoffee.userscreen;

/**
 * Created by The Dark on 01-Dec-2017.
 */

public class Object_UserProfile {
    private String FullName, Email, Gender, Birthday, ContactNumber, LinkAvatar;

    public Object_UserProfile() {
    }

    public Object_UserProfile(String fullName, String email, String gender, String birthday, String contactNumber, String linkAvatar) {
        FullName = fullName;
        Email = email;
        Gender = gender;
        Birthday = birthday;
        ContactNumber = contactNumber;
        LinkAvatar = linkAvatar;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getLinkAvatar() {
        return LinkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        LinkAvatar = linkAvatar;
    }
}
