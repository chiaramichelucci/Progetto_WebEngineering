<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Login PollWEB</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/log.css">
    </head>
    <body>
            <div class="login-page">
                <img src="img/Logo2.png" />
                <div class="form">
                      <form class="login-form" method="POST" action="login">
                        <input type="email" placeholder="email" name="email" />
                        <input type="password" placeholder="password" name="password" />
                        <input type="submit" name="login" id="login" value="login">
                        <p class="message">Non registrato? <a href="signin.ftl.html">Crea un account</a></p>
                      </form>
                </div>
              </div>
    </body>
</html>