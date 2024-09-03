<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TomatoClock</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- css -->
    <link rel="stylesheet" href="/resources/css/index.css">
</head>
<body>

    <!-- Include header.jsp -->
    <jsp:include page="includes/header.jsp" >
        <jsp:param name="showSettings" value="true" />
    </jsp:include>

    <section>
        <div class="container">

            <!-- Timer Display -->
            <div class="timer">
                <div class="timerDisplay">
                    <p id="minutes">25</p>
                    <p>:</p>
                    <p id="seconds">00</p>
                </div>
            </div>

            <!-- Timer Controls -->
            <div class="controls">
                <button id="startButton" >Start</button>

                <button id="stopButton" >Stop</button>

                <button id="resetButton">Reset</button>
            </div>

        </div>
    </section>

    <!-- Hidden input to track current session ID -->
    <input type="hidden" id="currentTimerSessionId" value="" />

    <!-- JQuery, Popper.js, Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

    <!-- script -->
    <script src="/resources/js/script.js"></script>

</body>
</html>
