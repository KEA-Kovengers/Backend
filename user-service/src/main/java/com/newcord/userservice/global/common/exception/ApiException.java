package com.newcord.userservice.global.common.exception;

import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.global.common.response.code.ErrorReasonDTO;

public class ApiException extends RuntimeException{
    private final ErrorStatus errorStatus;

    public ApiException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.errorStatus.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.errorStatus.getReasonHttpStatus();
    }

}
