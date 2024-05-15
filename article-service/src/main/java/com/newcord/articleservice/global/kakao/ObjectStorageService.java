package com.newcord.articleservice.global.kakao;

import com.newcord.articleservice.global.kakao.dto.S3Result;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// 일정시간마다 카카오에서 Token 발급

@Service
public class ObjectStorageService {
    @Autowired
    private S3Client s3Client;
    @Value("${cloud.kakao.object-storage.bucket}")
    private String bucketName;
    @Value("${cloud.kakao.object-storage.project-id}")
    private String projectID;
    @Value("${cloud.kakao.object-storage.endpoint}")
    private String endpoint;

    private void createBucket(String bucketName) {
        try {
            CreateBucketResponse res = s3Client.createBucket(
                CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void listBuckets() {
        try {
            ListBucketsResponse res = s3Client.listBuckets();
            System.out.println(res);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void listObjects(String bucketName) {
        try {
            ListObjectsResponse res = s3Client.listObjects(
                ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build());
            System.out.println(res);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // objectKey : 업로드할 파일명
// filePath : 업로드할 파일이 위치한 로컬 경로
    public List<String> uploadFile(List<MultipartFile> multipartFiles)  {
        List<String> fileList = new ArrayList<>();

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileList에 추가
        multipartFiles.forEach(file -> {
            try {
                Path path = Files.createTempFile(null,null);
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                String fileName = createFileName(file.getOriginalFilename());

                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

                s3Client.putObject(putObjectRequest, path);

                fileList.add(endpoint + "/v1/" + projectID + "/" + bucketName + "/" + fileName);

                Files.delete(path);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "파일 업로드에 실패했습니다.");
            }

        });
        return fileList;
    }

    // objectKey : 삭제할 파일명
    private void deleteObject(String bucketName, String objectKey){
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
