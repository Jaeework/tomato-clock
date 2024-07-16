package com.tomatoclock.service;

import com.tomatoclock.domain.UserSettingVO;
import com.tomatoclock.mapper.UserSettingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class UserSettingServiceImpl implements UserSettingService{

    private UserSettingMapper mapper;

    @Override
    public int saveUserSetting(UserSettingVO userSetting) {
        return mapper.saveUserSetting(userSetting);
    }

    @Override
    public UserSettingVO getUserSettingByUserId(String userId) {
        return mapper.selectUserSetting(userId);
    }

}
