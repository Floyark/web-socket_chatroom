<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Chat Room</title>
      <style>
        #record {
            min-width: 500px;
            padding-bottom: 10px;
        }
      </style>
   </head>
   <body>
      <h2>${message}</h2>
      <div class="user_div">
          <input type="text" placeholder="输入你的用户名" id="user_name" required="required">
          <input type="button" value="确定"  onclick="confirmUsername()">
      </div>
      <div id='record' class="chat_div"></div>
      <input type="text" id="input_message"maxlength=100 placeholder="在这里输入消息" required="required" size=50>
      <input type="button" value="发送" onclick="sendMessage()">
   </body>
<script>
         var wsClient
         var lastSendTime;
         function sendMessage(){
            if(lastSendTime == null){
                lastSendTime = new Date()
            }else if( new Date - lastSendTime < 700 ){
                return
            }else{
                lastSendTime = new Date();
            }

            var inputMessage = document.getElementById('input_message');
            if( inputMessage.value == '' ){
                alert("请输入内容");
            }else{
                window.wsClient.send(inputMessage.value)
            }
         }

         function confirmUsername(){
            var userName = document.getElementById("user_name").value;
            if ( userName==null || userName =='' ) { alert("用户名不能为空！") }
            else {
                window.wsClient = WsClient(userName)
            }
         }

         function WsClient( userName ){
             var wsClient = new WebSocket("ws://${ws_server}/chatroom?user_name="+userName+"");
             wsClient.onmessage = function(event){
                writeToScreen(event.data)
             }
             wsClient.onopen = function(event){
                  writeToScreen("your name is "+userName+ " now.")
             }
             wsClient.onclose = function(event){
                  writeToScreen("connect failed, please retry.")
             }
             return wsClient
         }

         function writeToScreen(text){
             var chatRecord = document.getElementById('record');
             chatRecord.innerHTML += text +"<br/>"
         }
      </script>
</html>