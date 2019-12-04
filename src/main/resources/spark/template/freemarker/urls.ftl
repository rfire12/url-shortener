<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="jumbotron my-5 shadow">
        <h4>URLs</h4>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Original</th>
                    <th>Shortified</th>
                    <th>User</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list urls as url>
                    <tr>
                        <td><a href="${url.originalVersion}">${url.originalVersion}</a></td>
                        <td><a href="/s/${url.shortVersion}"/>${url.shortVersion}</td>
                        <td>${url.user.username}</td>
                        <td>
                            <form action="/s/${url.shortVersion}/delete" method="post">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>