<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>TomatoClock</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/statistics.css">
</head>
<body>

    <!-- Include header.jsp -->
    <jsp:include page="includes/header.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-lg-8">
                <h2>Pomodoro 통계</h2>
                <div id="calendar" class="border rounded shadow-sm p-3 bg-white"></div>
                <div class="calender-footer">
                    <div class="color-legend col-lg-4">
                        <div class="color-box color-box-1">0+</div>
                        <div class="color-box color-box-2">4+</div>
                        <div class="color-box color-box-3">7+</div>
                        <div class="color-box color-box-4">10+</div>
                        <div class="color-box color-box-5">12+</div>
                    </div>
                    <div id="monthlyTotal" class="mt-auto"></div>
                </div>
            </div>
            <div class="col-lg-4">
                <h4 class="mt-4 mt-lg-0">일일 통계</h4>
                <div id="dayStats" class="border rounded shadow-sm p-3 bg-white text-center">
                    <h5 class="text-muted">Select a date to view statistics</h5>
                </div>
            </div>
        </div>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/statistics.js"></script>

</body>
</html>
