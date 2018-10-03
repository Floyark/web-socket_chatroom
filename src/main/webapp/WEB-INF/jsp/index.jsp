<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Welcome</title>
   </head>
   <body>
      <h2>${message}</h2>
      <ul>
        <li><a>聊天室一</a></li>
      </ul>
      <div id='record'></div>
   </body>
<script>
        var chatRecord = document.getElementById('record');
        var wsClient = new WebSocket("ws://localhost:8080/chatroom");
         wsClient.onopen = function(event){
            wsClient.send("I am htg.")
            write("Me: "+"I am coming.")
         }

         wsClient.onmessage = function(event){
            write("receive:"+event.data)
         }
         wsClient.onclose = function(event){
           write("error:"+event)
         }

         function write(text){
            chatRecord.innerHTML += text +"<br/>"
         }
      </script>
</html>