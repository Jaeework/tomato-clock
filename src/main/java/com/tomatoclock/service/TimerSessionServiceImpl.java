package com.tomatoclock.service;

import com.tomatoclock.domain.TimerSessionVO;
import com.tomatoclock.mapper.TimerSessionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    @Override
    public Map<String, Object> getStatistics(String userId, int year, int month) {
        List<TimerSessionVO> sessions = mapper.selectSessionListByMonth(userId, month, year);
        Map<String, Object> statistics = new HashMap<>();
        Map<String, Integer> dailyTotals = new HashMap<>();
        Map<String, Integer> dailyMaxDurations = new HashMap<>();

        if (!sessions.isEmpty()) {
            for (TimerSessionVO session : sessions) {
                String dateKey = new SimpleDateFormat("yyyy-MM-dd").format(session.getStarttime());
                int duration = session.getDuration();

                dailyTotals.put(dateKey, dailyTotals.getOrDefault(dateKey, 0) + duration);
                dailyMaxDurations.put(dateKey, Math.max(dailyMaxDurations.getOrDefault(dateKey, 0), duration));
            }
        }

        statistics.put("dailyMaxDurations", dailyMaxDurations); // 일별 최대 집중 시간 데이터
        statistics.put("dailyTotals", dailyTotals);  // 일별 총 시간 데이터
        statistics.put("sessions", sessions);        // 전체 세션 데이터

        return statistics;
    }

    @Override
    public TimerSessionVO getTimerSessionById(Long sessionId) {
        return mapper.getTimerSessionById(sessionId);
    }

    public Long createTimerSession(String userId) {
        TimerSessionVO session = new TimerSessionVO();
        session.setUserid(userId);
        session.setStarttime(new Date());
        session.setDuration(0);  // Set the initial planned duration

        mapper.insertTimerSession(session);

        log.info("Created new TimerSession: {}", session);
        return session.getId();
    }

    public void updateTimerSession(Long sessionId, int usedTime) {
        TimerSessionVO session = mapper.getTimerSessionById(sessionId);
        if (session != null) {
            Date currentTime = new Date();

            session.setEndtime(currentTime);
            session.setDuration(usedTime);

            mapper.updateTimerSession(session);

            log.info("Updated TimerSession: {}", session);
        } else {
            log.warn("Session ID not found: {}", sessionId);
        }
    }

}
