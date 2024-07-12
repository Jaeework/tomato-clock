<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>TomatoClock</title>
    <link rel="stylesheet" href="/resources/css/header.css">
</head>

<body>

    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><h1>TomatoClock</h1></a>
            <div class="ml-auto">
                <!-- Settings Dropdown -->
                <div class="dropdown d-inline dropstart">
                    <span class="settings-icon" id="settingsDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="/resources/img/setting-icon.png" alt="user setting icon">
                    </span>
                    <div class="dropdown-menu settings-dropdown-menu" aria-labelledby="settingsDropdown">
                        <form id="settingsForm" class="px-4 py-3">
                            <div class="form-group">
                                <label for="duration" class="form-label">Duration (minutes)</label>
                                <button type="button" class="btn btn-primary float-end mt-2" id="applyDuration">Apply</button>
                                <select class="form-control" id="duration">
                                  <c:forEach var="i" begin="10" end="60" step="5">
                                      <option value="${i}" ${i == 25 ? 'selected' : ''}>${i}</option>
                                  </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="backgroundColor" class="form-label">Background Color:</label>
                                <input type="color" class="form-control form-control-color" id="backgroundColor" value="#1f2241">
                            </div>
                            <div class="form-group">
                                <label for="shadowColor" class="form-label">Shadow Color:</label>
                                <input type="color" class="form-control form-control-color" id="shadowColor" value="#393e79">
                            </div>
                            <div class="form-group">
                                <label for="textColor" class="form-label">Text Color:</label>
                                <input type="color" class="form-control form-control-color" id="textColor" value="#ff69b4">
                            </div>
                            <div class="form-group">
                                <label for="backgroundImage" class="form-label">Background Image:</label>
                                <input type="file" class="form-control form-control-file" id="backgroundImage">
                            </div>
                            <button type="button" class="btn btn-primary float-end" id="saveSettings">Save</button>
                        </form>
                    </div>
                </div>

                <!-- User Dropdown -->
                <div class="dropstart dropdown d-inline dropstart">
                    <span class="user-icon" id="userDropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="/resources/img/profile-icon.png" alt="profile icon">
                    </span>
                    <div class="dropdown-menu user-dropdown-menu" aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="#">Profile</a>
                        <a class="dropdown-item" href="#">Statistics</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </nav>

</body>
</html>
