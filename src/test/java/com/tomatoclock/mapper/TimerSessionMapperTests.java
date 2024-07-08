package com.tomatoclock.mapper;

import com.tomatoclock.domain.TimerSessionVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j2
public class TimerSessionMapperTests {

    @Setter(onMethod_ = { @Autowired})
    private TimerSessionMapper mapper;

    @Test
    public void testInsertTimerSession() {
        TimerSessionVO session = new TimerSessionVO();
        session.setUserid("user00");
        session.setStarttime(new Date());
        session.setEndtime(null);
        session.setDuration(25);

        mapper.insertTimerSession(session);

        log.info(session);
    }

    @Test
    public void testSelectThisMonthTimerSession() {
        mapper.selectThisMonthTimerSessionList("user00").forEach(session -> {
            log.info(session);
        });
    }

}
