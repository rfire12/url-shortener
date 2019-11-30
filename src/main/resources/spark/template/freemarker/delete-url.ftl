<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <#if alert??>
        <div class="alert alert-${type} alert-dismissible fade show my-5 shadow" role="alert">
            ${alert}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </#if>
    <div class="jumbotron my-5">
        <h2>Delete an URL</h2>
        <br/>
        <form action="/delete-url" method="post">
            <div class="form-row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <input type="text" name="url" class="form-control" placeholder="Enter an URL">
                    </div>
                </div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-danger btn-block">Go</button>
                </div>
            </div>
        </form>
    </div>
</div>
<#include 'footer.ftl'>
</body>
</html>