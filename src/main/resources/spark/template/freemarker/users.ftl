<!doctype html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<div class="container">
    <div class="jumbotron my-5 shadow">
        <h4>Users</h4>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Admin</th>
                <th>No. urls</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.username}</td>
                    <td>${u.name}</td>
                    <#if u.admin>
                        <td><span class="badge badge-primary">Yes</span></td>
                    <#else>
                        <td><span class="badge badge-danger">No</span></td>
                    </#if>
                    <td>${u.myUrls?size}</td>
                    <td>
                        <div class="form-row">
                            <#if u.username != "admin">
                                <div class="mr-1">
                                    <form action="/${u.uid}/delete-user" method="post">
                                        <button type="submit" class="btn btn-danger">Delete</button>
                                    </form>
                                </div>
                                <#if !u.admin>
                                    <form action="/${u.uid}/upgrade-user" method="post">
                                        <button type="submit" class="btn btn-primary">Upgrade</button>
                                    </form>
                                <#else>
                                    <form action="/${u.uid}/downgrade-user" method="post">
                                        <button type="submit" class="btn btn-primary">Downgrade</button>
                                    </form>
                                </#if>
                            </#if>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<#include "footer.ftl">
</body>
</html>