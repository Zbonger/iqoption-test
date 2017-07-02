package com.iqoption.websocket.beans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

public class Message {

    private String name;

    @SerializedName("msg")
    private JsonElement message;

    public Message(String name, JsonElement message) {
        this.name = name;
        this.message = message;
    }

    public Message(String name, String message) {
        this.name = name;
        this.message = new JsonPrimitive(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonElement getMessage() {
        return message;
    }

    public <T> T getMessage(Class<T> clazz) {
        return new Gson().fromJson(getMessage(), clazz);
    }

    public void setMessage(JsonElement message) {
        this.message = message;
    }

}
