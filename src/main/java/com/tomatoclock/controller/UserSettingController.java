package com.tomatoclock.controller;

import com.tomatoclock.domain.UserSettingVO;
import com.tomatoclock.service.S3UploadService;
import com.tomatoclock.service.UserSettingService;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/api/users/me/settings")
public class UserSettingController {

    @Setter(onMethod_ = { @Autowired })
    private UserSettingService userSettingService;

    @Setter(onMethod_ = { @Autowired })
    private S3UploadService s3UploadService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserSettingVO> getSetting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 로그인한 사용자가 아닌 경우, 'anonymousUser' 를 반환

        UserSettingVO userSetting = userSettingService.getUserSettingByUserId(userId);

        // 설정이 없는 경우 기본값 반환
        if (userSetting == null) {
            userSetting = new UserSettingVO();
            userSetting.setUserId(userId);
            userSetting.setTxtColor("#E4E7F0");
            userSetting.setBgColor("#1A1E2E");
            userSetting.setShadowColor("#3A4157");
            userSetting.setDuration(25);
            userSetting.setBgImgName(null);
            userSetting.setBgImgUuid(null);
            userSetting.setBgImgPath(null);
        }

        log.warn("userSetting : " + userSetting);

        return ResponseEntity.ok(userSetting);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveSetting(@RequestBody UserSettingVO userSetting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        UserSettingVO existingSetting = userSettingService.getUserSettingByUserId(userId);
        if (existingSetting != null && existingSetting.getBgImgPath() != null) {
            // 새 설정의 배경 이미지 정보가 null이거나 기존 정보와 다른 경우 기존 파일 삭제
            if (userSetting.getBgImgPath() == null ||
                    !existingSetting.getBgImgUuid().equals(userSetting.getBgImgUuid())) {

                String existingFilePath = existingSetting.getBgImgPath() + File.separator +
                        existingSetting.getBgImgUuid() + "_" +
                        existingSetting.getBgImgName();
                s3UploadService.deleteFile(existingFilePath);
            }
        }

        userSetting.setUserId(userId);

        userSettingService.saveUserSetting(userSetting);

        return ResponseEntity.ok("success");
    }


    @PostMapping(value = "/background-image", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> uploadBackgroundImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> result = s3UploadService.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.warn("error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping(value = "/background-image", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteBackgroundImage(@RequestBody(required = false) Map<String, String> params) {
        String bgFilePath = params != null ? params.get("bgFilePath") : null;

        if (bgFilePath != null && !bgFilePath.isEmpty()) {
            s3UploadService.deleteFile(bgFilePath);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("no image to delete");
        }
    }

}
