<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>TomatoClock</title>
</head>
<style>
  .settings-dropdown-menu {
    min-width: 300px;
    padding: 10px;
    font-size: 1.1em;
  }
  .settings-dropdown-menu .form-control{
    margin-bottom: 15px;
  }

</style>
<body>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">TomatoClock</a>
  <div class="ml-auto">
    <!-- Settings Dropdown -->
    <div class="dropdown d-inline">
      <span class="settings-icon" id="settingsDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">&#9881;</span>
      <div class="dropdown-menu dropdown-menu-right settings-dropdown-menu" aria-labelledby="settingsDropdown">
        <form id="settingsForm" class="px-4 py-3">
          <div class="form-group">
            <label for="duration" class="form-label">Duration (minutes):</label>
            <select class="form-control" id="duration">
              <c:forEach var="i" begin="10" end="60" step="5">
                <option value="${i}">${i}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label for="backgroundColor" class="form-label">Background Color:</label>
            <input type="color" class="form-control form-control-color" id="backgroundColor" value="#000000">
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
    <div class="dropdown d-inline">
      <span class="user-icon" id="userDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">&#128100;</span>
      <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
        <a class="dropdown-item" href="#">Profile</a>
        <a class="dropdown-item" href="#">Statistics</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="#">Logout</a>
      </div>
    </div>
  </div>
</nav>


</body>
</html>
