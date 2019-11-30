<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="my-4">New
                <small>User</small>
            </h1>
            <form action="/create-user" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           placeholder="Enter the username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="new-password" name="password"
                           placeholder="Enter the password">
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="new-name" name="name" placeholder="Enter the name">
                </div>
                <button type="submit" class="btn btn-primary">Create User</button>
            </form>
        </div>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>