package com.newcord.articleservice.global.kakao.controller;

import com.newcord.articleservice.global.kakao.ObjectStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시글 서비스 오브젝트 스토리지 API", description = "게시글 서비스의 오브젝트 스토리지 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/object")
public class ObjectStorageController {
    private final ObjectStorageService s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> uploadObject(@RequestPart List<MultipartFile> files) {
        return s3Service.uploadFile(files);
    }
}
