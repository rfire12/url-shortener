<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>

<div class="container">
    <div class="jumbotron shadow" style="margin-top: 80px;">
        <h2>Shortify your URL!</h2>
        <br/>
        <form>
            <div class="form-row">
                <div class="col-sm-11">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Enter your URL">
                    </div>
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-primary btn-block">Go!</button>
                </div>
            </div>
        </form>
    </div>
    <br/>
    <div class="jumbotron shadow">
        <h4>Latest URLs</h4>
    </div>
</div>

<#include 'footer.ftl'>
</body>
</html>