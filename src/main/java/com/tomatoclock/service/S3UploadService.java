package com.tomatoclock.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String uploadFolder = getFolder();

        String uploadFileName = file.getOriginalFilename();
        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

        String uuid = UUID.randomUUID().toString();

        String fileName = uuid + "_" + uploadFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 업로드
        amazonS3.putObject(new PutObjectRequest(
                bucket,
                uploadFolder + "/" + fileName,
                file.getInputStream(),
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead));

        // 파일의 URL 가져오기
        String fileUrl = amazonS3.getUrl(bucket, uploadFolder + "/" + fileName).toString();

        Map<String, String> result = new HashMap<>();
        result.put("path", uploadFolder);
        result.put("uuid", uuid);
        result.put("name", uploadFileName);

        return result;
    }

    public void deleteFile(String filePath) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
            log.info("파일이 S3에서 삭제됨: " + filePath);
        } catch (Exception e) {
            log.error("파일 삭제 중 에러 발생: " + filePath, e);
            throw e;
        }
    }

    private String getFolder() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        String str = sdf.format(date);

        return str.replace("-", "/");
    }
}
