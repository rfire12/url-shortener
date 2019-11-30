<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>

<div class="container">
    <div class="jumbotron shadow" style="margin-top: 80px;">
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
            <h4>Latest URLs</h4>
            <ul>
                <#list latest as url>
                    <li>${url.originalVersion} - ${url.shortVersion}</li>
                </#list>
            </ul>
        </div>
    </#if>
</div>

<#include 'footer.ftl'>
</body>
</html>