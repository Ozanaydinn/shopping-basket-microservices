package com.springbank.user.query.api.dto;

import com.springbank.user.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserLookupResponse {
    private List<User> users;
    private String message;

    public UserLookupResponse(String message) {
        this.message = message;
        this.users = null;
    }

    public UserLookupResponse(String message, List<User> users ) {
        this.users = users;
        this.message = message;
    }

    public UserLookupResponse(String message, User user) {
        this.users = new ArrayList<>();
        users.add(user);
        this.message = message;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
