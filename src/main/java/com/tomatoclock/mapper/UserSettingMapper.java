package com.tomatoclock.mapper;

import com.tomatoclock.domain.UserSettingVO;

public interface UserSettingMapper {

    void insertUserSetting(UserSettingVO setting);

    void insertUserSettingSelectKey(UserSettingVO setting);

    UserSettingVO selectUserSetting(String userId);

}