package com.example.richardsenyange.breedlocator.constructors;

/**
 * Created by root on 14/2/18.
 */

public class chat_roomMessage {
    String message;
    int image;
    String name;
    String key;

    public chat_roomMessage() {
    }

    public chat_roomMessage(String message, String name, int image) {
        this.message = message;
        this.name = name;
        this.image=image;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "chat_roomMessage{" +
                "message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
