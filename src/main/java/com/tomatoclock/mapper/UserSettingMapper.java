package com.tomatoclock.mapper;

import com.tomatoclock.domain.UserSettingVO;

public interface UserSettingMapper {

    void insertUserSetting(UserSettingVO setting);

    UserSettingVO selectUserSetting(String userId);

}
