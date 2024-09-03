package com.tomatoclock.mapper;

import com.tomatoclock.domain.TimerSessionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimerSessionMapper {

    void insertTimerSession(TimerSessionVO timerSession);

    void updateTimerSession(TimerSessionVO timerSession);

    TimerSessionVO getTimerSessionById(Long sessionId);

    List<TimerSessionVO> selectThisMonthTimerSessionList(String userid);

    List<TimerSessionVO> selectSessionListByMonth(@Param("userid") String userid, @Param("month") int month, @Param("year") int year);

    List<TimerSessionVO> selectSessionByDate(@Param("userid") String userid, @Param("date") String date);
}
