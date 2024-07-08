package com.tomatoclock.service;

import com.tomatoclock.domain.MemberVO;
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
public class MemberServiceTests {

    @Setter(onMethod_ = { @Autowired })
    private MemberService service;

    @Test
    public void testCreateUser() {

        MemberVO member = new MemberVO();
        member.setId("user1");
        member.setPassword("pw00");
        member.setEmail("user1@email.com");

        service.createUser(member);
        log.info(member);
    }

    @Test
    public void testGetUserById(){
        log.info(service.getUserById("user1"));
    }

}
