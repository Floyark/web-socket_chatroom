<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Welcome</title>
      <script>
        var wsClient = new WebSocket("ws://localhost:8080/chatroom");
         wsClient.send("Here's some text that the server is urgently awaiting!");
      </script>
   </head>
   <body>
      <h2>${message}</h2>
      <ul>
        <li><a>聊天室一</a></li>
      </ul>
   </body>

</html>