package com.newcord.articleservice.controller;

import com.newcord.articleservice.global.kakao.ObjectStorageService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FirstController {
    private final ObjectStorageService s3Service;

    @GetMapping("/user/first")
    public String getFirst() {

        s3Service.listBuckets();
        return "first";
    }

    @PostMapping("/user/image")
    public String uploadObject(@RequestPart MultipartFile file) {
        List<MultipartFile> list = new ArrayList<>();
        list.add(file);
        return s3Service.uploadFile(list).get(0);
    }
}
