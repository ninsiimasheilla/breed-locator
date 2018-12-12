package com.example.richardsenyange.breedlocator.constructors;

/**
 * Created by root on 14/2/18.
 */

public class chat_roomUser {
    String uid;
    String name;
    String email;

    public chat_roomUser() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

  /*  @Override
    public String toString() {
        return "chat_roomUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }*/
}
