<!doctype html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="jumbotron shadow my-5">
        <div class="row">
            <div class="col-md-6">
                <h2>QR Code</h2>
                <div id="qrcode">
                </div>
            </div>
            <div class="col-md-6">
                <h2>Browsers</h2>
                <div id="browsers-graph">
                </div>
            </div>
        </div>
        <br/>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Shortified URL</th>
                    <th scope="col">Original URL</th>
                    <th scope="col">Created At</th>
                    <th scope="col">No. of Access</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row"><a href="${protocol}:${'//'}${host}/s/${url.shortVersion}"
                                       target="_blank">${protocol}
                            ://${host}/s/${url.shortVersion}</a></th>
                    <td>${url.originalVersion}</td>
                    <td>${date}</td>
                    <td>${accessCount}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">IP</th>
                    <th scope="col">Date</th>
                    <th scope="col">Browser</th>
                    <th scope="col">OS</th>
                    <th scope="col">Country</th>
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
</div>
<#include 'footer.ftl'>
<script src="/js/qrcode.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    $(function () {
        new QRCode(document.getElementById("qrcode"), {
            text: "${protocol}:${'//'}${host}/s/${url.shortVersion}",
            width: 128,
            height: 128,
        });

        google.charts.load('current', {packages: ['corechart']});
        google.charts.setOnLoadCallback(drawOSChart);

        function drawOSChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Browser');
            data.addColumn('number', 'Percentage');
            var dict = {};
            <#list access as a>
            dict['${a.browser}'] = 0;
            dict['${a.browser}'] += 1;
            </#list>
            console.log(dict);
            for (var key in dict) {
                data.addRow([key, dict[key]]);
            }
            var chart = new google.visualization.PieChart(document.getElementById("browsers-graph"));
            chart.draw(data, {
                title: "Access by Browsers",
                width: 450, height: 300,
            });
        }
    });
</script>
</body>
</html>