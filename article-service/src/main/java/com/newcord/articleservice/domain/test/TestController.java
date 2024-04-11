package com.newcord.articleservice.domain.test;

import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    * TestController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    @GetMapping("/success")
    public ApiResponse<String> test() {
        return ApiResponse.onSuccess("test");
    }

    @GetMapping("/fail")
    public ApiResponse<String> testFail() {
        return ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(), ErrorStatus._BAD_REQUEST.getMessage(), "test");
    }
}
