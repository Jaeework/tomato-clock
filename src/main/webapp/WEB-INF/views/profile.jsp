<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>TomatoClock</title>

  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- css -->
  <link rel="stylesheet" href="/resources/css/form.css">
</head>
<body>

<!-- Include header.jsp -->
<jsp:include page="includes/header.jsp" />

    <div class="container form-profile w-100 mt-5 m-auto">
        <sec:authentication property="principal" var="pinfo"/>
        <form id="profileForm" role="form" method="post" action="/updateProfile" class="user">
            <h1 class="h3 mb-3 fw-normal">Profile</h1>
            <div class="form-floating mb-3">
                <input type="text" class="form-control-plaintext" id="username" name="username" placeholder="ID" value="${pinfo.username}" readonly>
                <label for="username">ID</label>
            </div>
            <div class="form-floating mb-3">
                <input type="email" class="form-control-plaintext" id="email" name="email" placeholder="Email" value="${pinfo.member.email}" readonly>
                <label for="email">Email</label>
            </div>
            <div class="mb-3">
                <button type="button" class="btn btn-secondary mt-2" data-bs-toggle="modal" data-bs-target="#modal" onclick="showEmailChangeModal()">이메일 변경</button>
                <button type="button" class="btn btn-secondary mt-2" data-bs-toggle="modal" data-bs-target="#modal" onclick="showPasswordChangeModal()">비밀번호 변경</button>
            </div>

        </form>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalLabel">Change Information</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="changeForm">
                        <div id="emailChangeFields" style="display: none;">
                            <div class="form-floating mb-3">
                                <input type="email" class="form-control" id="newEmail" name="newEmail" placeholder="New Email">
                                <label for="newEmail">New Email</label>
                            </div>
                        </div>
                        <div id="passwordChangeFields" style="display: none;">
                            <div class="form-floating mb-3">
                                <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="New Password">
                                <label for="newPassword">New Password</label>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm New Password">
                                <label for="confirmPassword">Confirm New Password</label>
                            </div>
                        </div>
                        <div id="currentPasswordCheckFields">
                            <div class="form-floating mb-3">
                                <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="Current Password">
                                <label for="currentPassword">Current Password</label>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" id="saveChangesButton">Save changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- JQuery, Popper.js, Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

    <!-- script -->
    <script src="/resources/js/profile.js"></script>
    <script src="/resources/js/script.js"></script>

</body>
</html>
