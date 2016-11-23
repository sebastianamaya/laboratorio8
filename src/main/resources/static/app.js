var stompClient = null;
var canvas;
var context;
var PosX;
var PosY;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/app/newpoint', function (data) {
           data=JSON.parse(data.body);
           var x=data.x;
           var y=data.y;
           context.beginPath();
           context.arc(x,y,1,0,2*Math.PI);
           context.stroke();
           //alert("PosX="+x+" PosY="+y);

        });
        
        stompClient.subscribe('/topic/newpolygon', function (data) {
           data=JSON.parse(data.body);
           var puntos = data;
           
           context.fillStyle = '#f00';
           context.beginPath();
           context.moveTo(puntos[0].x, puntos[0].y);
           context.lineTo(puntos[1].x, puntos[1].y);
           context.lineTo(puntos[2].x, puntos[2].y);
           context.lineTo(puntos[3].x, puntos[3].y);
           context.closePath();
           context.fill();
   
           //alert("PosX="+x+" PosY="+y);

        });
        
        
        
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendPoint() {
    stompClient.send("/app/newpoint", {}, JSON.stringify({x:PosX,y:PosY}));
}

function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect();
        return {
          x: evt.clientX - rect.left,
          y: evt.clientY - rect.top
        };
      }
      
function manual_point(){
    PosX=document.getElementById('x').value;
    PosY=document.getElementById('y').value;
    sendPoint();
}
      
      
$(document).ready(
        function () {
            connect();
            canvas = document.getElementById('myCanvas');
            context = canvas.getContext('2d');
            console.info('connecting to websockets');
            
            canvas.addEventListener('mousedown', function(evt) {
            var mousePos = getMousePos(canvas, evt);
            PosX=mousePos.x;
            PosY=mousePos.y;
            sendPoint();
            }, false);
            
            
            canvas.addEventListener("touchstart",function(evt) {
            evt.preventDefault()
            PosX=event.targetTouches[0].pageX;
            PosY=event.targetTouches[0].pageY;
            sendPoint();
            }, false);
        }
        
);
