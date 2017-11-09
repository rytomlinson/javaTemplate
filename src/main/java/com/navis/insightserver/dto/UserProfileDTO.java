package com.navis.insightserver.dto;

/**
 * Created by ChrisDAgostino on 9/19/17.
 */
public class UserProfileDTO {

    private static final long serialVersionUID = 1L;
    private String token;
    private String state;
    private String userId;
    private String name;

    public UserProfileDTO(String userId, String Name, String token, String state)
    {
        this.token =token;
        this.name = name;
        this.userId = userId;
        this.state = state;

    }

    public String getUserId() {
        return userId;
    }
    public String getToken() {
        return token;
    }
    public String getState() {
        return state;
    }
    public String getName() {
        return name;
    }


}
