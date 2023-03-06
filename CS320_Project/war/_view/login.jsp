<!DOCTYPE html>

<html>
  <head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="./_view/css/style.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="./css/all.min.css">
    <link rel="stylesheet" href="https://kit.fontawesome.com/a839866b20.css" crossorigin="anonymous">

    <link href='https://fonts.googleapis.com/css?family=Playfair Display' rel='stylesheet'>
</head>

  <body>
    <div class="background-image"></div>
    <div class="login-box">
      <h1>Login </h1>
      <form action="login.php" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <h3><a href="forgot_password">Forgot Password?</a></h3>
        <button type="submit">Login</button>
        <br>
        <div class="social-media">
            <a href="https://www.facebook.com" target=""><i class="fab fa-facebook fa-2x"></i></a>
            <a href="https://www.twitter.com" target=""><i class="fab fa-twitter fa-2x"></i></a>
            <a href="https://www.youtube.com" target="_blank"><i class="fab fa-youtube fa-2x"></i></a>
            <a href="https://www.instagram.com" target="_blank"><i class="fab fa-instagram fa-2x"></i></a>
          </div>          
      </form>
      <h3>Don't have an account? <a href="signup">Sign up</a></h3>
    </div>
    <script src="https://kit.fontawesome.com/a839866b20.js" crossorigin="anonymous"></script>
  </body>
</html>
