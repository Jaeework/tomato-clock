package com.tomatoclock.mapper;

import com.tomatoclock.domain.UserSettingVO;

public interface UserSettingMapper {

    int saveUserSetting(UserSettingVO setting);

    UserSettingVO selectUserSetting(String userId);

}
