package com.example.myproperty;

public class userSession {
    private static userSession instance;
    private String userId;

    private userSession() {}

    public static userSession getInstance() {
        if (instance == null) {
            instance = new userSession();
        }
        return instance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void clear() {
        userId = null;
    }
}
