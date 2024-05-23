package com.newcord.userservice.domain.auth.response;

public enum ResponseCode {
    USER_LOGIN_SUCCESS("User login successful."),
    USER_NOT_FOUND("User not found"),
    USER_LOGIN_FAILURE("User login failed.");

    private final String message;

    ResponseCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
