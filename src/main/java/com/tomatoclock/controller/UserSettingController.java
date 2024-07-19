package com.tomatoclock.controller;

import com.tomatoclock.domain.UserSettingVO;
import com.tomatoclock.service.UserSettingService;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        }

        log.warn("userSetting : " + userSetting);

        return ResponseEntity.ok(userSetting);
    }

}
