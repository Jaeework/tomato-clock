<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Statistics</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/statistics.css">
</head>
<body>

    <!-- Include header.jsp -->
    <jsp:include page="includes/header.jsp" />

    <div class="container">
        <div class="header">
            <h2>Pomodoro 통계</h2>
        </div>

        <div class="calendar">
            <div class="month-nav">
                <button id="prev-month">&lt;</button>
                <h3 id="current-month-year"></h3>
                <button id="next-month">&gt;</button>
            </div>
            <div class="weekdays">
                <div>일</div>
                <div>월</div>
                <div>화</div>
                <div>수</div>
                <div>목</div>
                <div>금</div>
                <div>토</div>
            </div>
            <div id="calendar-days" class="days"></div>
        </div>

        <div id="stats" class="stats"></div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="/resources/js/statistics.js"></script>

</body>
</html>
