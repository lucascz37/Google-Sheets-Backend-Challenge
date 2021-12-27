<!DOCTYPE html>
<html>
    <head>
        <title>Marketing Data Viewer</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="/fav/favicon.png">
        <link href="/css/gridAndFlex.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>

    <body>
        <nav class="navbar navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">
                    Marketing Data Viewer
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/">Masters</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/married">Married</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/kids">Kids</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/teens">Teens</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/born">Birth Year</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="grid-display mt-5">
            <p>Filtered by: ${filtered} </p>
            <p>Total consumers: ${consumers.totalOfConsumers?string.number}</p>
            <p>Filtered consumers: ${consumers.filteredConsumers?string.number}</p>
            <p>Percentage of filtered consumers: ${consumers.filteredConsumers?string.percent}</p>
        </div>

        <div class="grid-display mt-5">
            <#if (consumers.consumerList?size > 0)>
                <#list consumers.consumerList as consumer>
                    <div class="grid-item card mt-5" style="width: 18rem;">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Consumer id: ${consumer.id!}</li>
                            <li class="list-group-item">Birth year: ${consumer.yearBirth?replace(',', '', 'r')!}</li>
                            <li class="list-group-item">Consumer education: ${consumer.education!}</li>
                            <li class="list-group-item">Marital status: ${consumer.maritalStatus!}</li>
                            <li class="list-group-item">Kids home: ${consumer.kidHome!}</li>
                            <li class="list-group-item">Teens status: ${consumer.teenHome!}</li>
                        </ul>
                    </div>
                </#list>
            </#if>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>

</html>