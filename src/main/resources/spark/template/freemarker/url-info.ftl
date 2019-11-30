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
        <div class="row">
            <div class="col-md-6">
                <h2>Date</h2>
                <div id="date-graph">
                </div>
            </div>
            <div class="col-md-6">
                <h2>OS</h2>
                <div id="os-graph">

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
                </tr>
                </thead>
                <tbody>
                <#list access as a>
                    <tr>
                        <th scope="row">${a.ip}</th>
                        <td>${a.date}</td>
                        <td>${a.browser}</td>
                        <td>${a.os}</td>
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
        google.charts.setOnLoadCallback(drawBrowserChart);
        google.charts.setOnLoadCallback(drawDateChart);
        google.charts.setOnLoadCallback(drawOSChart);

        function drawDateChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Date');
            data.addColumn('number', 'Times');
            var dict = {}
            <#list access as a>
            dict['${a.date}'] = 0;
            </#list>
            <#list access as a>
            dict['${a.date}'] += 1;
            </#list>
            for (var key in dict) {
                data.addRow([key, dict[key]]);
            }
            var chart = new google.visualization.ColumnChart(document.getElementById("date-graph"));
            chart.draw(data, {
                title: "Access by Date",
                width: 450,
                height: 300,
            });
        }

        function drawBrowserChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Browser');
            data.addColumn('number', 'Percentage');
            var dict = {};
            <#list access as a>
            dict['${a.browser}'] = 0;
            </#list>
            <#list access as a>
            dict['${a.browser}'] += 1;
            </#list>
            for (var key in dict) {
                data.addRow([key, dict[key]]);
            }
            var chart = new google.visualization.PieChart(document.getElementById("browsers-graph"));
            chart.draw(data, {
                title: "Access by Browsers",
                width: 450, height: 300,
            });
        }

        function drawOSChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'OS');
            data.addColumn('number', 'Percentage');
            var dict = {};
            <#list access as a>
            dict['${a.os}'] = 0;
            </#list>
            <#list access as a>
            dict['${a.os}'] += 1;
            </#list>
            for (var key in dict) {
                data.addRow([key, dict[key]]);
            }

            var chart = new google.visualization.PieChart(document.getElementById("os-graph"));
            chart.draw(data, {
                title: "Access by OS",
                width: 450, height: 300,
            })
        }
    });
</script>
</body>
</html>