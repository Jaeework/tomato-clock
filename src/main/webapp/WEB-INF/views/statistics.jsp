<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>TomatoClock</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css">
    <link rel="stylesheet" href="/resources/css/statistics.css">
</head>
<body>

    <!-- Include header.jsp -->
    <jsp:include page="includes/header.jsp" />

<div class="container mt-4">
    <h2 class="mb-4">Pomodoro Statistics</h2>

    <div class="row">
        <div class="col-md-8">
            <div id="calendar"></div>
        </div>
        <div class="col-md-4">
            <div id="stats" class="card">
                <div class="card-body">
                    <h5 class="card-title">Daily Statistics</h5>
                    <div id="dayStats"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
<script src="/resources/js/statistics.js"></script>

</body>
</html>
