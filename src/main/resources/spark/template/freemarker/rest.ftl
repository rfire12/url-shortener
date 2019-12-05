<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="jumbotron shadow my-5">
        <h3>Generate token</h3>
        <p>Generate a JWT to use our API service. List and create links in an instant. You can generate as many as you want.</p>
        <form method="post" action="/generate-jwt">
            <label for="start">Token expiration date:</label>
            <br/>
            <input type="date" id="start" name="jwt-date" value="2019-12-04" min="2019-12-04">
            <input type="submit" name="submit" value="Generate">
        </form>
            <br/><br/>
            <#if jwt??>
                <p style="word-wrap:break-word;">Your JWT is: ${jwt}</p><br/>
                <#if jwt_exp??>
                    <span>It expires on: ${jwt_exp}</span>
                </#if>
            </#if>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>