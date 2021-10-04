package com.example.a5SGonher;

import android.app.Application;

public class GlobalClass extends Application {

    private String name="https://vvnorth.com/";
    private String email;

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
}
