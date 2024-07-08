package com.tomatoclock.service;

import com.tomatoclock.domain.UserSettingVO;

public interface UserSettingService {

    public void createUserSetting(UserSettingVO userSettings);

    public UserSettingVO getUserSettingByUserId(String userId);

}
