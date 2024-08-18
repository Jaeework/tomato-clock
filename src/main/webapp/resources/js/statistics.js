let currentDate = new Date();

function renderCalendar(year, month) {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startingDay = firstDay.getDay();

    let calendarHtml = '';
    let dayCount = 1;

    for (let i = 0; i < 42; i++) {
        if (i < startingDay || dayCount > daysInMonth) {
            calendarHtml += '<div class="day"></div>';
        } else {
            calendarHtml += `<div class="day" data-date="${year}-${(month + 1).toString().padStart(2, '0')}-${dayCount.toString().padStart(2, '0')}">${dayCount}</div>`;
            dayCount++;
        }
    }

    document.getElementById('calendar-days').innerHTML = calendarHtml;
    document.getElementById('current-month-year').textContent = `${year}년 ${month + 1}월`;

    fetchMonthData(year, month + 1);
}

function fetchMonthData(year, month) {
    $.getJSON('/api/statistics/getByMonth/' + year + "/" + month,
        function(data) {
            updateCalendarHighlights(data)
        }).fail(function(xhr, status, error) {
        console.error("Error fetching month data:", error);
    });
}

function updateCalendarHighlights(sessions) {
    const dailyTotals = {};
    sessions.forEach(session => {
        const date = new Date(session.starttime).toISOString().split('T')[0];
        dailyTotals[date] = (dailyTotals[date] || 0) + session.duration;
    });

    Object.keys(dailyTotals).forEach(function(date) {
        const duration = dailyTotals[date];
        console.log("duration ", date, " : ", dailyTotals);
        const dayElement = document.querySelector(`.day[data-date="${date}"]`);

        if (dayElement) {
            if (duration >= 43200) {          // over 12 hours
                dayElement.classList.add('highlight-5');
            } else if (duration >= 36000) {   // over 10 hours
                dayElement.classList.add('highlight-4');
            } else if (duration >= 25200) {   // over 7 hours
                dayElement.classList.add('highlight-3');
            } else if (duration >= 14400) { // over 4 hours
                dayElement.classList.add('highlight-2');
            } else {
                dayElement.classList.add('highlight-1');
            }
            const durationText = `${Math.floor(duration / 3600).toString().padStart(2, '0')}:${Math.floor((duration % 3600) / 60).toString().padStart(2, '0')}`;
            dayElement.innerHTML += `<br>${durationText}`;
        }
    });

}

function fetchDayStats(date) {
    $.getJSON("/api/statistics/getByDate/" + date,
        function(stats) {
            displayDayStats(stats, date);
        }).fail(function(xhr, status, error) {
        console.error("Error fetching day stats:", error);
    });
}

function displayDayStats(stats, date) {
    const totalDuration = stats.totalDuration;
    const startTime = stats.startTime ? new Date(stats.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';
    const endTime = stats.endTime ? new Date(stats.endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false }) : '';
    const hours = Math.floor(totalDuration / 3600);
    const minutes = Math.floor((totalDuration % 3600) / 60);
    const seconds = totalDuration % 60;

    let statsHtml = `
        <h4>${date}</h4>
        <p>총 집중 시간: ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}</p>
        <p>시작시간: ${startTime}</p>
        <p>종료시간: ${endTime}</p>
    `;

    document.getElementById('stats').innerHTML = statsHtml;
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('calendar-days').addEventListener('click', function(e) {
        if (e.target.classList.contains('day') && e.target.dataset.date) {
            document.querySelectorAll('.day').forEach(day => day.classList.remove('active'));
            e.target.classList.add('active');
            fetchDayStats(e.target.dataset.date);
        }
    });

    document.getElementById('prev-month').addEventListener('click', function() {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate.getFullYear(), currentDate.getMonth());
    });

    document.getElementById('next-month').addEventListener('click', function() {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate.getFullYear(), currentDate.getMonth());
    });

    // Initial render
    renderCalendar(currentDate.getFullYear(), currentDate.getMonth());

    // Set default selected date to today
    const today = new Date().toISOString().split('T')[0];
    document.querySelector(`.day[data-date="${today}"]`).classList.add('active');
    fetchDayStats(today);
});
