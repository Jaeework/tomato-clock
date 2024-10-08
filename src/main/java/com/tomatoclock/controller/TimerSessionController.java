package com.tomatoclock.controller;

import com.tomatoclock.domain.TimerSessionVO;
import com.tomatoclock.service.TimerSessionService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/users/me/timer-sessions")
@Log4j2
@AllArgsConstructor
public class TimerSessionController {

    @Setter(onMethod_ = @Autowired)
    private TimerSessionService sessionService;

    @GetMapping("/statistics/{year}/{month}")
    public ResponseEntity<Map<String, Object>> getStatistics(@PathVariable("year") int year, @PathVariable("month") int month, Principal principal) {
        String userId = principal.getName();
        log.warn("getStatistics controller: userid=" + userId + ", year=" + year + ", month=" + month);

        Map<String, Object> statistics = sessionService.getStatistics(userId, year, month);

        return ResponseEntity.ok(statistics);
    }


    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<TimerSessionVO>> getSessionsByMonth(@PathVariable("year") int year, @PathVariable("month") int month, Principal principal) {
        String userId = principal.getName();

        List<TimerSessionVO> sessions = sessionService.getSessionListByMonth(userId, month, year);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<Map<String, Object>> getSessionByDate(@PathVariable("date") String date, Principal principal) {
        // 특정 날짜의 세션 데이터를 반환
        String userId = principal.getName();

        Map<String, Object> stats = sessionService.getSessionStatsByDate(userId,date);
        return ResponseEntity.ok(stats);
    }

    @PostMapping
    public ResponseEntity<String> createTimerSession(Principal principal) {
        String userId = principal.getName();
        Long sessionId = sessionService.createTimerSession(userId);
        return ResponseEntity.ok(String.valueOf(sessionId));
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<String> updateTimerSession(@PathVariable("sessionId") Long sessionId, @RequestBody Map<String, String> params) {
        int usedTime = Integer.parseInt(params.get("usedTime"));

        sessionService.updateTimerSession(sessionId, usedTime);
        return ResponseEntity.ok("Session updated successfully.");
    }

}

