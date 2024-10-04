document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    let monthlyTotal = 0;

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

            $.getJSON('/api/users/me/timer-sessions/statistics/' + year + "/" + month, function(data) {
                const dailyTotals = data.dailyTotals;
                const dailyMaxDurations = data.dailyMaxDurations;
                const sessions = data.sessions;

                monthlyTotal = 0;

                const events = Object.keys(dailyTotals).map(date => {
                    const totalDuration = dailyTotals[date];
                    const maxDuration = dailyMaxDurations[date];
                    monthlyTotal += totalDuration;

                    return {
                        title: `${Math.floor(totalDuration / 3600).toString().padStart(2, '0')}:${Math.floor((totalDuration % 3600) / 60).toString().padStart(2, '0')}`,
                        start: date,
                        allDay: true,
                        display: 'background',
                        backgroundColor: getColorForDuration(totalDuration),
                        borderColor: getColorForDuration(totalDuration),
                        textColor: '#000000',
                        extendedProps: {
                            totalDuration: totalDuration,
                            maxDuration: maxDuration,
                            sessions: sessions.filter(session => {
                                // starttime을 ISO 문자열로 변환하고 날짜 부분만 비교
                                const startDateStr = new Date(session.starttime).toISOString().split('T')[0];
                                return startDateStr === date;
                            })
                        }
                    };
                });
                successCallback(events);
                updateMonthlyTotal();
            }).fail(function(xhr, status, error) {
                console.error("Error fetching month data:", error);
                failureCallback(error);
            });
        },
        eventClick: function(info) {
            const date = info.event.startStr;
            const selectedSessions = info.event.extendedProps.sessions;
            displayDayStats(date, info.event.extendedProps.totalDuration, info.event.extendedProps.maxDuration, selectedSessions);
        }
    });

    calendar.render();

    function updateMonthlyTotal() {
        const hours = Math.floor(monthlyTotal / 3600);
        const minutes = Math.floor((monthlyTotal % 3600) / 60);
        const totalHTML = `
            <div>
                <strong>월간 총 집중시간:</strong> ${hours}H ${minutes}M
            </div>
        `;
        document.getElementById('monthlyTotal').innerHTML = totalHTML;
    }

    function getColorForDuration(duration) {
        if (duration >= 43200) return '#ff4500';    // over 12 hours
        if (duration >= 36000) return '#fe712d';    // over 10 hours
        if (duration >= 25200) return '#fe9e5a';    // over 7 hours
        if (duration >= 14400) return '#fdca86';    // over 4 hours
        if (duration >= 1) return '#fcf8b5';        // over 1 second
        return '#F9F9F9';   // Default
    }

    function displayDayStats(date, totalDuration, maxDuration, sessions) {
        const startTime = sessions.length > 0 ? new Date(sessions[0].starttime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';
        const endTime = sessions.length > 0 ? new Date(sessions[sessions.length - 1].endtime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';

        let statsHtml = `
            <h5 class="stat-date mt-4 mb-4">${date}</h5>
            <p class="stat-item mb-4"><strong>총 집중 시간</strong><br/> ${formatDuration(totalDuration)}</p>
            <p class="stat-item mb-4"><strong>최대 집중 시간</strong><br/> ${formatDuration(maxDuration)}</p>
            <p class="stat-item mb-4"><strong>시작시간</strong><br/> ${startTime}</p>
            <p class="stat-item mb-4"><strong>종료시간</strong><br/> ${endTime}</p>
        `;

        document.getElementById('dayStats').innerHTML = statsHtml;
    }

    function formatDuration(seconds) {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
        const remainingSeconds = seconds % 60;

        return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(remainingSeconds).padStart(2, '0')}`;
    }

});
