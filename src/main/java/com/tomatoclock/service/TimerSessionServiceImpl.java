package com.tomatoclock.service;

import com.tomatoclock.domain.TimerSessionVO;
import com.tomatoclock.mapper.TimerSessionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class TimerSessionServiceImpl implements TimerSessionService {

    private TimerSessionMapper mapper;

    @Override
    public void createTimerSession(TimerSessionVO timerSession) {
        mapper.insertTimerSession(timerSession);
    }

    @Override
    public List<TimerSessionVO> getThisMonthTimerSessionList(String userid) {
        return mapper.selectThisMonthTimerSessionList(userid);
    }
}
