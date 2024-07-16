package com.tomatoclock.controller;

import com.tomatoclock.domain.UserSettingVO;
import com.tomatoclock.service.UserSettingService;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/settings")
public class UserSettingController {
    @Setter(onMethod_ = { @Autowired })
    private UserSettingService userSettingService;

    @PostMapping(value = "/save", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveSetting(@RequestBody UserSettingVO userSetting) {

        userSettingService.saveUserSetting(userSetting);

        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/get/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserSettingVO> getSetting(@PathVariable("userId") String userId) {
        UserSettingVO userSetting = userSettingService.getUserSettingByUserId(userId);
        return ResponseEntity.ok(userSetting);
    }

}
