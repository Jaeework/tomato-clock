package com.tomatoclock.mapper;

import com.tomatoclock.domain.UserSettingVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j2
public class UserSettingMapperTests {

    @Setter(onMethod_ = { @Autowired })
    private UserSettingMapper mapper;

    @Test
    public void testSaveUserSetting() {

        UserSettingVO setting = new UserSettingVO();
        setting.setUserId("user00");
        setting.setDuration(20);
        setting.setTxtColor("#fffff6");
        setting.setShadowColor("#393e79");
        setting.setBgColor("#1f2241");
        setting.setBgImgUuid(null);
        setting.setBgImgName(null);

        mapper.saveUserSetting(setting);
    }

    @Test
    public void testSelect() {

        UserSettingVO setting = mapper.selectUserSetting("user00");

        log.info(setting);
    }
}
