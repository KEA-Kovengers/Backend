package com.newcord.articleservice.global.common.response.code.status;

import com.newcord.articleservice.global.common.response.code.BaseErrorCode;
import com.newcord.articleservice.global.common.response.code.ErrorReasonDTO;
import java.util.HashMap;
import java.util.Map;
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

    // Message DTO 관련
    _BAD_REQUEST_DTO(HttpStatus.BAD_REQUEST, "COMMON400", "Request DTO 내용이 잘못되었습니다."),

    _ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE_001", "존재하지 않는 게시글입니다."),
    _ARTICLE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ARTICLE_002", "이미 존재하는 게시글입니다."),

    //Block 관련
    _BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "BLOCK_001", "존재하지 않는 블록입니다."),
    _BLOCK_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "BLOCK_002", "이미 존재하는 블록입니다."),

    //Posts 관련
    _POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "POSTS_001", "존재하지 않는 게시글입니다."),

    //Editor 관련
    _EDITOR_WITHOUT_PERMISSION(HttpStatus.BAD_REQUEST, "EDITOR_001", "편집 권한이 없습니다."),
    _EDITOR_NOT_FOUND(HttpStatus.NOT_FOUND, "EDITOR_002", "존재하지 않는 편집자입니다."),

    //Hashtags 관련
    _HASHTAGS_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAGS_001", "존재하지 않는 해시태그입니다."),
    _HASHTAGS_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "HASHTAGS_002", "이미 존재하는 해시태그입니다."),

    //Comments 관련
    _COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"COMMENTS_001","존재하지 않는 댓글입니다."),

    //Report 관련
    _REPORT_NOT_FOUND(HttpStatus.NOT_FOUND,"REPORT_001","신고 내역이 존재하지 않습니다."),
    _REPORT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"REPORT_002","같은 내용의 신고 내역이 존재합니다.");

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
