package com.tomatoclock.controller;

import com.tomatoclock.domain.UserSettingVO;
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
@RequestMapping("/api/settings")
public class UserSettingController {
    @Setter(onMethod_ = { @Autowired })
    private UserSettingService userSettingService;

    @PostMapping(value = "/save", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveSetting(@RequestBody UserSettingVO userSetting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        userSetting.setUserId(userId);

        userSettingService.saveUserSetting(userSetting);

        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/get", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserSettingVO> getSetting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 로그인한 사용자가 아닌 경우, 'anonymousUser' 를 반환

        UserSettingVO userSetting = userSettingService.getUserSettingByUserId(userId);

        // 설정이 없는 경우 기본값 반환
        if (userSetting == null) {
            userSetting = new UserSettingVO();
            userSetting.setUserId(userId);
            userSetting.setTxtColor("#fffff6");
            userSetting.setBgColor("#1f2241");
            userSetting.setShadowColor("#393e79");
            userSetting.setDuration(25);
            userSetting.setBgImgName(null);
            userSetting.setBgImgUuid(null);
            userSetting.setBgImgUrl(null);
        }

        log.warn("userSetting : " + userSetting);

        return ResponseEntity.ok(userSetting);
    }

    @PostMapping(value = "/uploadBackgroundImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> uploadBackgroundImage(@RequestParam("file") MultipartFile file) {
        try {

            String uploadFolder = "D:" + File.separator + "upload";
            String uploadFolderPath = getFolder();

            // make folder
            File uploadPath = new File(uploadFolder, uploadFolderPath);
            log.info("upload path : " + uploadPath);

            if(uploadPath.exists() == false) {
                uploadPath.mkdirs();
            }

            String uploadFileName = file.getOriginalFilename();

            // IE has file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            log.info("only file name : " + uploadFileName);

            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + uploadFileName;
            file.transferTo(new File(uploadPath, fileName));

            String uploadUrl = uploadFolderPath.replace(File.separator, "/") + "/" + fileName;
            log.info("uploadUrl : " + uploadUrl);

            Map<String, String> response = new HashMap<>();
            response.put("uploadUrl", uploadUrl);
            response.put("uuid", uuid);
            response.put("name", uploadFileName);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.warn("error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String getFolder() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        String str = sdf.format(date);

        return str.replace("-", File.separator);
    }

    @PostMapping(value = "/deleteBackgroundImage", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteBackgroundImage(@RequestBody(required = false) Map<String, String> params) {
        String bgImgUrl = params != null ? params.get("bgImgUrl") : null;

        if (bgImgUrl != null && !bgImgUrl.isEmpty()) {
            deleteBackgroundImageFile(bgImgUrl);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("no image to delete");
        }
    }

    private void deleteBackgroundImageFile(String bgImgUrl) {
        if (bgImgUrl != null && !bgImgUrl.isEmpty()) {
            String filePath = "D:/upload/" + bgImgUrl;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
