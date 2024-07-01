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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #000;
            color: hotpink;
            text-align: center;
        }
        .timer {
            font-size: 5em;
            margin-top: 20%;
        }
        .settings-icon, .user-icon {
            font-size: 1.5em;
            color: white;
            cursor: pointer;
        }
        .settings-modal .modal-body {
            color: black;
        }
    </style>
</head>
<body>
<!-- Include header.jsp -->
<jsp:include page="includes/header.jsp" />

<!-- Timer Display -->
<div class="timer">
    <span id="timerDisplay">25:00</span>
</div>

<!-- Timer Controls -->
<div class="mt-4">
    <button id="startButton" class="btn btn-success">Start</button>
    <button id="stopButton" class="btn btn-danger" style="display:none;">Stop</button>
</div>

</body>
</html>
