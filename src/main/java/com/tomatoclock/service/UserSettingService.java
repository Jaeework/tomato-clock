package com.tomatoclock.service;

import com.tomatoclock.domain.UserSettingVO;

public interface UserSettingService {

    public int saveUserSetting(UserSettingVO userSetting);

    public UserSettingVO getUserSettingByUserId(String userId);

}
