package com.tomatoclock.mapper;

import com.tomatoclock.domain.TimerSessionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimerSessionMapper {

    void insertTimerSession(TimerSessionVO timerSession);
    List<TimerSessionVO> selectThisMonthTimerSessionList(String userid);

}
