package com.newcord.userservice.global.common.response.code.status;

import com.newcord.userservice.global.common.response.code.BaseErrorCode;
import com.newcord.userservice.global.common.response.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * 에러 응답 코드
 * 도메인별로 에러 코드와 원인을 추가해주세요
 */
@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    //일반 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    //friend 관련
    _ALREADY_FRIEND(HttpStatus.BAD_REQUEST,"FRIEND_002_","이미 친구입니다."),
    _ALREADY_REQUEST(HttpStatus.BAD_REQUEST,"FRIEND_003","이미 친구 요청을 보냈습니다."),
    _FRIEND_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND,"FRIEND_004","친구 요청을 찾을 수 없습니다."),

    _FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLDER_001", "존재하지 않는 폴더입니다."),
    _FOLDER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "FOLDER_002", "이미 존재하는 폴더입니다."),

    _FOLDERPOST_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLDERPOST_001", "폴더에서 존재하지 않는 게시글입니다."),
    _FOLDERPOST_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "FOLDERPOST_002", "폴더에 이미 존재하는 게시글입니다."),

    _USER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_001", "데이터베이스에 존재하지 않는 사용자입니다."),
    _ACCESS_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AUTH_002", "엑세스 토큰이 만료되었습니다."),
    _REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AUTH_003", "리프레시 토큰이 만료되었습니다."),
    _INVALID_AUTH_HEADER(HttpStatus.BAD_REQUEST, "AUTH_004", "잘못된 헤더입니다."),;



    private final HttpStatus httpStatus;        // HTTP 상태 코드
    private final String code;                  // 내부적인 에러 코드. 도메인명 + 숫자로 구성. 숫자는 HTTP 상태코드 100의자리 참고
    private final String message;               // FE에 전달할 오류 메세지

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}
