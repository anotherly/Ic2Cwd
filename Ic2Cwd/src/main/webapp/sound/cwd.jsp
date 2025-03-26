 <!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>
    <script>
  	console.log("혼잡1");
  	
    var node = document.querySelector('#audio1');
    node.play();
    
  	console.log("혼잡2");
  	
  	var audio = new Audio('seve_sound.mp3');
    audio.play();
  	
    console.log("혼잡3");
    
  </script>
</head>
<body>
  <audio src="cwd_sound.mp3" id="audio1" loop></audio>
</body>
</html>