package com.newcord.userservice.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"success","code","message","result"})
public class ApiResponse<T> {

    @JsonProperty("success")
    private final boolean success;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ApiResponse<T> success(T result, String message) {
        return new ApiResponse<>(true, message, result);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}

