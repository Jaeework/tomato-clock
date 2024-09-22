<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
<head>
    <title>TomatoClock</title>
    <link rel="stylesheet" href="/resources/css/header.css">
    <%
        boolean showSettings = Boolean.valueOf(request.getParameter("showSettings"));
    %>
</head>

<body>
    <c:set var="showSettings" value="<%=showSettings%>" />

    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/"><h1>TomatoClock</h1></a>
            <div class="ml-auto">
                <!-- Settings Dropdown -->
                <c:if test="${not empty showSettings and showSettings == true}">
                    <div class="dropdown d-inline dropstart">
                        <span class="settings-icon" id="settingsDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="/resources/img/setting-icon.png" alt="user setting icon">
                        </span>
                        <div class="dropdown-menu settings-dropdown-menu border-0 shadow w-220px" aria-labelledby="settingsDropdown" data-bs-theme="dark">
                            <form id="settingsForm" class="px-4 py-3">
                                <div class="form-group">
                                    <label for="duration" class="form-label">Duration (minutes)</label>
                                    <button type="button" class="btn btn-primary float-end mt-2" id="applyDuration">Apply</button>
                                    <select class="form-control" id="duration">
                                      <c:forEach var="i" begin="10" end="60" step="5">
                                          <option value="${i}">${i}</option>
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
                                    <input type="color" class="form-control form-control-color" id="textColor" value="#fffff6">
                                </div>
                                <div class="form-group background-image-form-group">
                                    <label for="backgroundImage" class="form-label">Background Image:</label>
                                    <input type="file" class="form-control form-control-file" accept="image/*" id="backgroundImage">
                                    <div id="fileInfo" style="display: none;">
                                        <span id="fileName"></span>
                                        <button id="removeFile" class="remove-file-button">
                                            <img class="cancel-icon" src="/resources/img/cancel-icon.png" alt="Remove file">
                                        </button>
                                    </div>
                                </div>
                                <div class="save-setting-button">
                                    <button type="button" class="btn btn-primary float-end" id="saveSettings">Save</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:if>

                <!-- User Dropdown -->
                <div class="dropstart dropdown d-inline dropstart">
                    <span class="user-icon" id="userDropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="/resources/img/profile-icon.png" alt="profile icon">
                    </span>
                    <div class="dropdown-menu user-dropdown-menu border-0 shadow w-220px" aria-labelledby="userDropdown" data-bs-theme="dark">
                        <a class="dropdown-item" href="/profile">Profile</a>
                        <a class="dropdown-item" href="/statistics">Statistics</a>
                        <div class="dropdown-divider"></div>
                        <sec:authorize access="isAuthenticated()">
                            <form id="logoutForm" class="user" role="form" method="post" action="/logout">
                                <a class="dropdown-item" href="/" id="logoutButton">Logout</a>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            </form>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <a class="dropdown-item" href="/login">Login</a>
                        </sec:authorize>
                    </div>
                </div>
            </div>
        </div>
    </nav>

</body>
</html>
