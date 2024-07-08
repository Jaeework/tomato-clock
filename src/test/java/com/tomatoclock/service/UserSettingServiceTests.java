package com.tomatoclock.service;

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
public class UserSettingServiceTests {

    @Setter(onMethod_ = { @Autowired })
    private UserSettingService service;

    @Test
    public void testCreateUserSetting() {

        UserSettingVO userSetting = new UserSettingVO();
        userSetting.setUserId("user1");

        service.createUserSetting(userSetting);

        log.info("생성된 유저 세팅 번호  : " + userSetting.getId());
    }

    @Test
    public void testGetUserSettingByUserId() {

        log.info(service.getUserSettingByUserId("user1"));

    }

}