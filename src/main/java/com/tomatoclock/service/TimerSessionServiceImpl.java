package com.tomatoclock.service;

import com.tomatoclock.domain.TimerSessionVO;
import com.tomatoclock.mapper.TimerSessionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<TimerSessionVO> getSessionListByMonth(String userid, int month, int year) {
        return mapper.selectSessionListByMonth(userid, month, year);
    }

    @Override
    public Map<String, Object> getSessionStatsByDate(String userid, String date) {
        List<TimerSessionVO> sessions = mapper.selectSessionByDate(userid, date);
        Map<String, Object> stats = new HashMap<>();

        int totalDuration = 0;
        Date startTime = null;
        Date endTime = null;

        if (!sessions.isEmpty()) {
            startTime = sessions.get(0).getStarttime();
            endTime = sessions.get(0).getEndtime();

            for (TimerSessionVO session : sessions) {
                totalDuration += session.getDuration();

                if (startTime == null || session.getStarttime().before(startTime)) {
                    startTime = session.getStarttime();
                }
                if (endTime == null || session.getEndtime() != null && session.getEndtime().after(endTime)) {
                    endTime = session.getEndtime();
                }
            }
        }

        stats.put("totalDuration", totalDuration);
        stats.put("startTime", startTime);
        stats.put("endTime", endTime);

        return stats;
    }
}
