package com.tomatoclock.service;

import com.tomatoclock.domain.TimerSessionVO;

import java.util.List;

public interface TimerSessionService {

    public void createTimerSession(TimerSessionVO timerSession);

    public List<TimerSessionVO> getThisMonthTimerSessionList(String userid);

}
