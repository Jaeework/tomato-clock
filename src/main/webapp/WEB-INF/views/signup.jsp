<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/form.css">
</head>
<body>

    <div class="container form-signup w-100 mt-5 m-auto">
        <form role="form" method="post" action="/signup" class="user">
            <h1 class="logo" onclick="location.href='/'">TomatoClock</h1>
            <h1 class="h3 mb-3 fw-normal">Sign Up</h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="username" placeholder="ID" name="id">
                <label for="username">ID</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="password" placeholder="Password" name="password">
                <label for="password">Password</label>
            </div>
            <div class="form-floating">
                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                <label for="email">Email</label>
            </div>
            <button class="btn btn-primary w-100 py-2" type="submit">Sign up</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </div>

</body>
</html>
