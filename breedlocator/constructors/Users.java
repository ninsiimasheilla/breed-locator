package com.example.richardsenyange.breedlocator.constructors;

/**
 * Created by RICHARD SENYANGE on 12/6/2018.
 */

public class Users {

    public String name;
    public String image;
    public String status;
    public String thumb_image;
    public  String user_category;



    public Users(){

    }

    public Users(String name, String image, String status, String thumb_image,String uer_category) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.user_category=uer_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getUser_category() {return user_category;}

    public void setUser_category(String user_category) {this.user_category = user_category;}
}
