document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        timeZone: 'local',
        initialView: 'dayGridMonth',
        showNonCurrentDates: false,
        fixedWeekCount: false,
        headerToolbar: {
            left: '',
            center: 'title',
            right: 'prev,next today'
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            const year = fetchInfo.start.getFullYear();
            const month = fetchInfo.start.getMonth() + 1; // FullCalendar uses 0-indexed months

            $.getJSON('/api/statistics/getStatistics/' + year + "/" + month, function(data) {
                const dailyTotals = data.dailyTotals;
                const sessions = data.sessions;

                const events = Object.keys(dailyTotals).map(date => {
                    const duration = dailyTotals[date];

                    return {
                        title: `${Math.floor(duration / 3600).toString().padStart(2, '0')}:${Math.floor((duration % 3600) / 60).toString().padStart(2, '0')}`,
                        start: date,
                        allDay: true,
                        display: 'background',
                        backgroundColor: getColorForDuration(duration),
                        borderColor: getColorForDuration(duration),
                        textColor: '#000000',
                        extendedProps: {
                            duration: duration,
                            sessions: sessions.filter(session => {
                                // starttime을 ISO 문자열로 변환하고 날짜 부분만 비교
                                const startDateStr = new Date(session.starttime).toISOString().split('T')[0];
                                return startDateStr === date;
                            })
                        }
                    };
                });
                successCallback(events);

            }).fail(function(xhr, status, error) {
                console.error("Error fetching month data:", error);
                failureCallback(error);
            });
        },
        eventClick: function(info) {
            const date = info.event.startStr;
            const selectedSessions = info.event.extendedProps.sessions;
            displayDayStats(date, info.event.extendedProps.duration, selectedSessions);
        }
    });

    calendar.render();

    function getColorForDuration(duration) {
        if (duration >= 43200) return '#ff4500';    // over 12 hours
        if (duration >= 36000) return '#FF9800';    // over 10 hours
        if (duration >= 25200) return '#f8bc5e';    // over 7 hours
        if (duration >= 14400) return '#e0c080';    // over 4 hours
        if (duration >= 1) return '#f1d7b6';        // over 1 second
        return '#F9F9F9';   // Default
    }

    function displayDayStats(date, totalDuration, sessions) {
        const startTime = sessions.length > 0 ? new Date(sessions[0].starttime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';
        const endTime = sessions.length > 0 ? new Date(sessions[sessions.length - 1].endtime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';
        const hours = Math.floor(totalDuration / 3600);
        const minutes = Math.floor((totalDuration % 3600) / 60);
        const seconds = totalDuration % 60;

        let statsHtml = `
            <h5>${date}</h5>
            <p>총 집중 시간: ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}</p>
            <p>시작시간: ${startTime}</p>
            <p>종료시간: ${endTime}</p>
        `;

        document.getElementById('dayStats').innerHTML = statsHtml;
    }
});
