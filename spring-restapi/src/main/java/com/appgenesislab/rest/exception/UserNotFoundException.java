package com.appgenesislab.rest.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super("user#" + userId + " was not found");
        this.userId = userId;
    }

    private long userId;

    public long getUserId() {
        return userId;
    }

}
