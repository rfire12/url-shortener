<!doctype html>
<html lang="en">
<#include 'head.ftl'>
    <body>
        <#include 'navbar.ftl'>

            <div class="container">
                <div class="jumbotron shadow" style="margin-top: 80px;">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Url acortada</th>
                            <th scope="col">Url Original</th>
                            <th scope="col">Fecha de creacion</th>
                            <th scope="col">Cantidad de accesos</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th scope="row"><a href="${protocol}:${'//'}${host}/s/${url.shortVersion}" target="_blank">${protocol}://${host}/s/${url.shortVersion}</a></th>
                            <td>${url.originalVersion}</td>
                            <td>${date}</td>
                            <td>${accessCount}</td>
                        </tr>
                        </tbody>
                    </table>

                    <h2>Accesos</h2>
                    <table class="table">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">IP</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Browser</th>
                            <th scope="col">OS</th>
                            <th scope="col">Pais</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list access as a>
                            <tr>
                                <th scope="row">${a.ip}</th>
                                <td>${a.date}</td>
                                <td>${a.browser}</td>
                                <td>${a.os}</td>
                                <td>${a.country}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
            </div>

        <#include 'footer.ftl'>
    </body>
</html>