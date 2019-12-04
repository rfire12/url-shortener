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
            <div class="table table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Preview</th>
                        <th>Original</th>
                        <th>Shortified</th>
                        <th colspan="2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list latest as url>
                        <tr id="url-${url.shortVersion}">
                            <td class="preview">

                            </td>
                            <td>
                                ${url.originalVersion}
                            </td>
                            <td class="urlShort ${url.shortVersion}">
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
        </div>
    </#if>
</div>
<#include 'footer.ftl'>
<script type="application/javascript">
    $(function () {
        $('[id^="url-"]').each(function () {
            var myUrl = $(this).find(".urlShort").attr('class').split(' ')[1];
            $.ajax({
                url: "https://api.linkpreview.net/?key=5de82b007f6d0ee5d57044e005d0f8104161e20b42286&q=${host}s/" + myUrl,
            }).done(function () {
                $("#url-" + myUrl).find(".preview").text("DONE");
            });
        });
    });
</script>
</body>
</html>