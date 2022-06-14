<!doctype html>
<html lang="ru">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
</head>

<body>
<div class="d-flex flex-column vh-100 p-3">

    <div>
        <h3 class="text-center">Добавление клиента</h3>
        <form method="post" action="/add">
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Имя</span>
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                    </div>
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Адрес</span>
                            <input type="text" class="form-control" id="street" name="street">
                        </div>
                    </div>
                    <div class="col-5">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Телефоны</span>
                            <input type="text" class="form-control" id="phone1" name="phoneNumbers">
                            <span class="input-group-text">,</span>
                            <input type="text" class="form-control" id="phone2" name="phoneNumbers">
                            <span class="input-group-text">,</span>
                            <input type="text" class="form-control" id="phone3" name="phoneNumbers">
                        </div>
                    </div>
                    <div class="col-1">
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div class="flex-grow-1">
        <h3 class="text-center">Список клиентов</h3>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Имя</th>
                <th scope="col">Адрес</th>
                <th scope="col">Телефоны</th>
            </tr>
            </thead>
            <tbody>
            <#list clients as client>
                <tr>
                    <th scope="row">${client.id}</th>
                    <td>${client.name}</td>
                    <td>${client.address.street}</td>
                    <td>${client.phones?map(phone -> phone.number)?join(", ")}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>
</body>

</html>