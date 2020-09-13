<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SignIn PollWEB</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/log.css">
        <script type="text/javascript" src="js/controlloCampi.js" charset="UTF-8"></script>
    <body>
            <div class="login-page">
                <img src="img/Logo2.png" />
                <div class="form">
                    <form class="register-form" method="post" name="reg" id ="reg">
                        <input type="text" placeholder="nome" name="nome" />
                        <input type="text" placeholder="cognome" name="cognome" />
                        <input type="email" placeholder="email address" name="email" />
                        <input type="password" placeholder="password" name="password" />
                        <input type="password" placeholder="conferma password" name="conferma" />
                        <button onclick="datireg()">Crea</button>
                        <p class="message">Gi� registrato? <a href="login.html">Log In</a></p>
                    </form>
                </div>
              </div>
    </body>
</html>