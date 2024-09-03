package com.tomatoclock.service;

import com.tomatoclock.domain.TimerSessionVO;

import java.util.List;
import java.util.Map;

public interface TimerSessionService {

    public void createTimerSession(TimerSessionVO timerSession);

    public List<TimerSessionVO> getThisMonthTimerSessionList(String userid);

    public List<TimerSessionVO> getSessionListByMonth(String userid, int month, int year);

    public Map<String,Object> getSessionStatsByDate(String userid, String date);

    Map<String, Object> getStatistics(String userId, int year, int month);

    Long createTimerSession(String userId);

    void updateTimerSession(Long sessionId, int usedTime);

    TimerSessionVO getTimerSessionById(Long sessionId);

}
