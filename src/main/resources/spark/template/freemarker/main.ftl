<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="jumbotron shadow my-5">
        <h2>Shortify your URL!</h2>
        <br/>
        <form action="/shortify" method="post">
            <div class="form-row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <input type="text" name="url" class="form-control" placeholder="Enter your URL">
                    </div>
                </div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary btn-block">Go!</button>
                </div>
            </div>
        </form>
    </div>
    <br/>
    <#if latestSize gt 0>
        <div class="jumbotron shadow">
            <#if user??>
                <h4>My shortened urls</h4>
            <#else>
                <h4>Shortened urls on this browser</h4>
            </#if>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Original</th>
                    <th>Shortified</th>
                    <th colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <#list latest as url>
                    <tr>
                        <td>
                            ${url.originalVersion}
                        </td>
                        <td>
                            <a href="${host}s/${url.shortVersion}" target="_blank">${host}s/${url.shortVersion}</a>
                        </td>
                        <td>
                            <#if user??>
                                <a href="/info/${url.shortVersion}" class="btn btn-info">Info</a>
                            </#if>
                        </td>
                        <td>
                            <#if user??>
                                <form action="/s/${url.shortVersion}/delete" method="post">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </#if>
</div>
<#include 'footer.ftl'>
</body>
</html>