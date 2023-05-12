<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" 
              href="webjars/bootstrap/5.2.3/css/bootstrap.min.css" type="text/css" />
    </head>
    <body>
        <div class="container mt-5">
            <form name="form" action="/WebSistema/ServletVerifica" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Usuario</label>
                    <input type="text" name="xnom" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label">Password</label>
                    <input type="password" name="xcla" class="form-control" id="exampleInputPassword1">
                </div>
                <button type="submit" class="btn btn-primary">Ingresar</button>
<a class='btn btn-danger' href="index.html">Cancelar</a>
            </form>
        </div>
    </body>

</html>