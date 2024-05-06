package com.newcord.articleservice.global.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.newcord.articleservice.global.common.response.code.BaseCode;
import com.newcord.articleservice.global.common.response.code.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


// 웹소켓 응답, RabbitMQ에 전달할 메세지에 사용할 Wrapper class
// 웹소켓 응답은 isSuccess, uuid, dest, code, message, result로 구성
// isSuccess: 성공 여부
// uuid: 요청 식별자 (클라이언트가 요청 보낼때 생성해야함)
// dest: 요청 목적지 (어떤 메소드로 인해 생성된 메세지인지 식별하기 위함)
// code: 응답 코드
// message: 응답 메세지
// result: 응답 데이터
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "uuid", "dest", "code", "message", "result"})
public class WSResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String uuid;
    private final String dest;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 응답 생성
    public static <T> WSResponse<T> onSuccess(String dest, String uuid, T result) {
        return new WSResponse<>(true, uuid, dest, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(),
            result);
    }

    public static <T> WSResponse<T> of(String dest, String uuid, BaseCode code, T result) {
        return new WSResponse<>(true, uuid, dest,  code.getReasonHttpStatus().getCode(),
            code.getReasonHttpStatus().getMessage(), result);
    }

    // 실패한 경우 응답 생성
    public static <T> WSResponse<T> onFailure(String dest, String uuid, String code, String message, T data) {
        return new WSResponse<>(false, uuid, dest, code, message, data);
    }
}
