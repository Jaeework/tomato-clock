package com.tomatoclock.mapper;

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
public class MemberMapperTests {

    @Setter(onMethod_ = { @Autowired })
    private MemberMapper mapper;

    @Test
    public void testInsert() {

            MemberVO member = new MemberVO();
            member.setId("user00");
            member.setPassword("pw00");
            member.setEmail("user00@email.com");

            mapper.insertUser(member);

            log.info(member);
    }

    @Test
    public void testSelectById() {
        MemberVO member = mapper.selectById("user00");

        log.info(member);
    }

    @Test
    public void testSelectByEmail() {
        MemberVO member = mapper.selectByEmail("user00@email.com");

        log.info(member);
    }
}
