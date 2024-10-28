package com.lab.darackbang.common.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.lab.upload.path}")
    private  String uploadPath;

    @Value("${com.lab.eventUpload.path}")
    private  String eventUploadPath;

    /**
     * 프로젝트 실행시 무조건 실행
     */
    @PostConstruct
    public void init() throws IOException {

        Path tempFolderPath = Paths.get(uploadPath);
        // 디렉토리가 존재하지 않으면 생성하고, 이미 존재하면 아무 작업도 하지 않음
        Files.createDirectories(tempFolderPath);
        // 절대 경로로 변환
        uploadPath = tempFolderPath.toAbsolutePath().toString();

        log.info("******파일 업로드 경로:{}", uploadPath);

    }

    /**
     * 업로드 이미지 가져 뷰에서 보기
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getFile(String fileName){
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        if(!resource.exists()){
            resource = new FileSystemResource(uploadPath+File.separator+"default.png");
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        try{
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

    /**
     * 업로드 이벤트 이미지 가져 뷰에서 보기
     * @param fileName
     * @return
     */
    public ResponseEntity<Resource> getEventFile(String fileName){
        Resource resource = new FileSystemResource(eventUploadPath+File.separator+fileName);

        if(!resource.exists()){
            resource = new FileSystemResource(eventUploadPath+File.separator+"default.png");
        }

        HttpHeaders httpHeaders = new HttpHeaders();

        try{
            httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

}
